package ua.kh.em.rickmo.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ua.kh.em.rickmo.R
import ua.kh.em.rickmo.data.model.Character
import ua.kh.em.rickmo.databinding.FragmentDetailBinding
import ua.kh.em.rickmo.utils.ToastUtil
import ua.kh.em.rickmo.utils.isNetConn


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getToolbar()
        handleParcel()
    }

    private fun getToolbar() {
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_detail)
    }

    private fun handleParcel() {
        val bundle = arguments?.getParcelable("detail") as Character?
        if (bundle != null) {
            if (context.isNetConn()) {
                val imageDetail: String? = bundle.image
                val nameDetail: String? = bundle.name
                val statusDetail: String? = bundle.status
                val speciesDetail: String? = bundle.species
                val genderDetail: String? = bundle.gender

                Picasso.get()
                    .load(imageDetail)
                    .error(R.drawable.ic_emoji)
                    .placeholder(R.drawable.ic_emoji)
                    .fit()
                    .into(binding.detailImage)

                binding.detailName.text = nameDetail
                binding.detailStatus.text = statusDetail
                binding.detailSpecies.text = speciesDetail
                binding.detailGender.text = genderDetail
            } else {
                ToastUtil.toastSimple(context, R.string.no_internet)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}