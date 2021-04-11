package ua.kh.em.rickmo.ui.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.kh.em.rickmo.R
import ua.kh.em.rickmo.data.model.Character
import ua.kh.em.rickmo.data.model.CharacterList
import ua.kh.em.rickmo.databinding.FragmentMainBinding
import ua.kh.em.rickmo.ui.adapter.MainAdapter
import ua.kh.em.rickmo.ui.viewmodel.MainViewModel
import ua.kh.em.rickmo.utils.NetCheck
import ua.kh.em.rickmo.utils.SortUtil
import ua.kh.em.rickmo.utils.ToastUtil
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var list = ArrayList<Character>()
    private var adapter: MainAdapter? = null
    private var disposable: CompositeDisposable? = null
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    // To inflate activity menu from fragment.
    // Call setHasOptionsMenu(boolean) in onCreate(Bundle)
    // to notify the fragment that it should participate
    // in options menu handling.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.show = true
        getToolbar()
        setupRecyclerView()
        processResponse()
    }

    private fun getToolbar() {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_title)
    }

    private fun processResponse() {
        if (NetCheck.isNetExists(context)) {
            getData()
        } else {
            binding.show = false
            ToastUtil.toastSimple(context, R.string.no_internet)
        }
    }

    private fun setupRecyclerView() {
        binding.listItems.layoutManager = GridLayoutManager(
                context, 2, LinearLayoutManager.VERTICAL, false
        )
        binding.listItems.setHasFixedSize(true)
        adapter = MainAdapter(list)
        binding.listItems.adapter = adapter
    }

    private fun getData() {
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ elements: CharacterList? -> handleResponse(elements) }) {
                throwable: Throwable? -> handleError(throwable)
            }?.let { disposable?.add(it) }
    }

    private fun handleResponse(items: CharacterList?) {
        binding.show = false
        val elements: ArrayList<Character>? = items?.listCharacter
        elements?.let { adapter?.addListData(it) }
    }

    private fun handleError(throwable: Throwable?) {
        binding.show = false
        ToastUtil.toastSimple(context, R.string.something_wrong)
        Log.e("Sort ERROR", throwable.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable?.clear()
        }
        _binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
        val layoutButton = menu.findItem(R.id.item_sort)
        setIcon(layoutButton)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
        menuItem.icon =  ContextCompat.getDrawable(
                this.requireContext(), R.drawable.ic_sort)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_sort -> {
                // Sort data
                getSortData()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSortData() {
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadData()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ elements: CharacterList? -> sortData(elements) }) {
                    throwable: Throwable? -> handleError(throwable)
            }?.let { disposable?.add(it) }

    }

    private fun sortData(items: CharacterList?) {
        binding.show = false
        val elements: ArrayList<Character>? = items?.listCharacter
        val sortedList = elements?.let { SortUtil.sortCharactersByName(it) }
        sortedList.let { it?.let { it1 -> adapter?.addListData(it1) } }
    }

}