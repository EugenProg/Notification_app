package kz.just_code.notificationapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kz.just_code.notificationapp.databinding.FragmentStartBinding

class StartFragment: Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notificationManage = MyNotificationManager(requireContext())

        if (requireActivity().intent.hasExtra("ACTION")) {
            Toast.makeText(requireContext(), requireActivity().intent.getStringExtra("ACTION"), Toast.LENGTH_SHORT).show()
        }

        binding.showBtn.setOnClickListener {
            notificationManage.showMessage(requireContext())
        }

        binding.hideBtn.setOnClickListener {
            notificationManage.clear(requireContext())
        }

        requireActivity().intent.data?.let {
            it.queryParameterNames.forEach { name ->
                Log.e(">>>", "$name: ${it.getQueryParameter(name)}")
            }
        }
    }
}