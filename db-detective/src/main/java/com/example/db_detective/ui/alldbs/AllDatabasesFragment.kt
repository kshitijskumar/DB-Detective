package com.example.db_detective.ui.alldbs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.databinding.FragmentAllDatabasesBinding
import com.example.db_detective.navigation.IDBDetectiveNavigation

class AllDatabasesFragment : Fragment() {

    private var _binding: FragmentAllDatabasesBinding? = null
    private val binding: FragmentAllDatabasesBinding get() = _binding!!

    private lateinit var navHelper: IDBDetectiveNavigation

    private val allDbsAdapter: AllDatabasesAdapter by lazy {
        AllDatabasesAdapter {
            navHelper.navigateToTablesDataFragment(it.tableName)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (requireActivity() is IDBDetectiveNavigation) {
            navHelper = requireActivity() as IDBDetectiveNavigation
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDatabasesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupDatabasesName()
    }

    private fun initViews() {
        binding.rvAlldbs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allDbsAdapter
        }
    }

    private fun setupDatabasesName() {
        val allDbs = DBDetective.getAllDatabaseNames()
        val dbAndTableModelList = mutableListOf<SingleDatabaseModel>()
        allDbs.forEach { dbName ->
            val tablesForDb = DBDetective.getAllTablesFromDatabase(dbName)
            val singleDbModels = tablesForDb.map { SingleDatabaseModel(dbName, it) }
            dbAndTableModelList.addAll(singleDbModels)
        }
        allDbsAdapter.submitList(dbAndTableModelList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAGMENT_TAG = "AllDatabasesFragment"
    }

}