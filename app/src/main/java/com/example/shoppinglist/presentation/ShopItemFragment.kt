package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val mode: String = MODE_UNKNOWN,
    private val id: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var ti_name: TextInputLayout
    private lateinit var ti_count: TextInputLayout
    private lateinit var et_name: TextInputEditText
    private lateinit var et_count: TextInputEditText
    private lateinit var but_OK: Button
    private lateinit var viewModel: ShopItemViewModel

    //private var mode: String = MODE_UNKNOWN
    //private var id: Int = ShopItem.UNDEFINED_ID
    private var name: String = NAME_SHOP_ITEM
    private var count: String = COUNT_SHOP_ITEM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        init(view)
        chooseRightRegim()
        addViewModelObserves()
        addTextEditListeners()
    }


    private fun addViewModelObserves() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) ti_name.error = getString(R.string.name_input_error) else ti_name.error = null
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) ti_count.error = getString(R.string.count_input_error) else ti_count.error =
                null
        }

        viewModel.canCloseAcivity.observe(viewLifecycleOwner) {
            finish()
        }
    }

    private fun addTextEditListeners() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
                ti_name.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        et_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
                ti_count.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


    private fun chooseRightRegim() {
        when (mode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(id)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            et_name.setText(it.name)
            et_count.setText(it.id.toString())
        }
        but_OK.setOnClickListener {
            getNameAndCount()
            viewModel.editElement(name, count)
        }
    }

    private fun launchAddMode() {
        but_OK.setOnClickListener {
            getNameAndCount()
            viewModel.addElement(name, count)
        }
    }

    private fun getNameAndCount() {
        name = et_name.text.toString()
        count = et_count.text.toString()
    }

    private fun parseParams() {
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException ("Param screen mode is absent")
        }
        if (mode == MODE_EDIT && id==ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Params ID is not found")
        }
      /*  if (intent.hasExtra(EXTRA_MODE)) {
            mode = intent.getStringExtra(EXTRA_MODE).toString()
            if (mode == MODE_EDIT || mode == MODE_ADD) {
                if (mode != MODE_ADD) {
                    if (intent.hasExtra(EXTRA_MODE)) {
                        id = intent.getIntExtra(EXTRA_ID, -1)
                        if (id < 0) throw RuntimeException("Params ID is out of range")
                    } else throw RuntimeException("Params ID is not found")
                }
            } else throw RuntimeException("Params EXTRA MODE is out of range")
        } else throw RuntimeException("Params EXTRA MODE is not found")*/
    }

    private fun init(view:View) {
        ti_name = view.findViewById(R.id._ti_name)
        ti_count = view.findViewById(R.id._ti_count)
        et_name = view.findViewById(R.id._et_name)
        et_count = view.findViewById(R.id._et_count)
        but_OK = view.findViewById(R.id._butt_ok)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_ID = "extra_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val NAME_SHOP_ITEM = ""
        private const val COUNT_SHOP_ITEM = ""

        fun newIntentAddItem(ctx: Context): Intent {
            val intent = Intent(ctx, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(ctx: Context, id: Int): Intent {
            val intent = Intent(ctx, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }


    }
}