package com.example.db_detective.ui.tableinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.db_detective.core.manager.DBDetectiveInteractionManager
import com.example.db_detective.core.manager.IDBDetectiveInteractionManager
import com.example.db_detective.core.utils.handleManagerSuccessOrShowToast
import com.example.db_detective.databinding.FragmentTablesDataBinding
import com.example.db_detective.ui.adapter.TableLayoutAdapter

class TablesDataFragment : Fragment() {

    private var _binding: FragmentTablesDataBinding? = null
    private val binding: FragmentTablesDataBinding get() = _binding!!

    private val tableAdapter: TableLayoutAdapter by lazy { TableLayoutAdapter() }

    private val tableName: String by lazy { arguments?.getString(ARG_TABLE_NAME) ?: "" }

    private val dbInteractionManager: IDBDetectiveInteractionManager = DBDetectiveInteractionManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTablesDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAllData()
    }

    private fun initAllData() {
        val allDataFromTable = dbInteractionManager.getEntireDataOfTable(tableName)
        handleManagerSuccessOrShowToast(
            result = allDataFromTable
        ) {
            tableAdapter.submitList(it.rowEntries)
        }
    }

    private fun initView() {
        binding.rvTable.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tableAdapter
        }

        binding.btnRefresh.setOnClickListener {
            val userQuery = binding.etCustomQuery.text.toString()
            val customQuery = userQuery.ifEmpty {
                initAllData()
                return@setOnClickListener
            }
            updateDataForCustomQuery(customQuery)
        }

        binding.btnRunQuery.setOnClickListener {
            val userQuery = binding.etCustomQuery.text.toString()
            if (userQuery.isNotEmpty()) {
                updateDataForCustomQuery(userQuery)
            }
        }
    }

    private fun updateDataForCustomQuery(customQuery: String) {
        val dataForCustomQuery = dbInteractionManager.runCustomQueryOnTable(tableName, customQuery)
        handleManagerSuccessOrShowToast(
            dataForCustomQuery
        ) {
            tableAdapter.submitList(it.rowEntries)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val FRAGMENT_TAG = "TablesDataFragment"
        private const val ARG_TABLE_NAME = "arg_tableName"

        fun createInstance(tableName: String): TablesDataFragment {
            return TablesDataFragment().apply {
                val bundle = bundleOf(ARG_TABLE_NAME to tableName)
                arguments = bundle
            }
        }
    }

}