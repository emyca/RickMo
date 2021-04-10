package ua.kh.em.rickmo.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import ua.kh.em.rickmo.utils.ToastUtil
import java.util.*


class MainFragment : Fragment() {

    private var list = ArrayList<Character>()
    private var adapter: MainAdapter? = null
    private var disposable: CompositeDisposable? = null
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        binding.show = true
        setupRecyclerView()
        processResponse()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_child)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable?.clear()
        }
        _binding = null
    }

}