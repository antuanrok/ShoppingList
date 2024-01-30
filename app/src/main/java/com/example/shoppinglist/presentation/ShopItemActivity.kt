package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    /*private lateinit var ti_name: TextInputLayout
    private lateinit var ti_count: TextInputLayout
    private lateinit var et_name: TextInputEditText
    private lateinit var et_count: TextInputEditText
    private lateinit var but_OK: Button
    private lateinit var viewModel: ShopItemViewModel*/
    private var mode: String = MODE_UNKNOWN
    private var id: Int = ShopItem.UNDEFINED_ID
   // private var name: String = NAME_SHOP_ITEM
   // private var count: String = COUNT_SHOP_ITEM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        /*viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        init()*/
        chooseRightRegim()
       /*addViewModelObserves()
        addTextEditListeners()*/
    }

   /* private fun addViewModelObserves(){
        viewModel.errorInputName.observe(this) {
            if (it) ti_name.error = getString(R.string.name_input_error) else ti_name.error = null
        }
        viewModel.errorInputCount.observe(this) {
            if (it) ti_count.error = getString(R.string.count_input_error) else ti_count.error =
                null
        }

        viewModel.canCloseAcivity.observe(this) {
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
    }*/


    private fun chooseRightRegim() {
        val fragment = when (mode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(id)
            else -> throw RuntimeException("Unknown screen mode $mode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id._shop_item_container,fragment)
            .commit()
    }
/*
    private fun launchEditMode() {
        viewModel.getShopItem(id)
        viewModel.shopItem.observe(this) {
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
*/
    private fun parseIntent() {
        if (intent.hasExtra(EXTRA_MODE)) {
            mode = intent.getStringExtra(EXTRA_MODE).toString()
            if (mode == MODE_EDIT || mode == MODE_ADD) {
                if (mode != MODE_ADD) {
                    if (intent.hasExtra(EXTRA_MODE)) {
                        id = intent.getIntExtra(EXTRA_ID, -1)
                        if (id < 0) throw RuntimeException("Params ID is out of range")
                    } else throw RuntimeException("Params ID is not found")
                }
            } else throw RuntimeException("Params EXTRA MODE is out of range")
        } else throw RuntimeException("Params EXTRA MODE is not found")
    }

/*
    private fun init() {
        ti_name = findViewById(R.id._ti_name)
        ti_count = findViewById(R.id._ti_count)
        et_name = findViewById(R.id._et_name)
        et_count = findViewById(R.id._et_count)
        but_OK = findViewById(R.id._butt_ok)
    }*/

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_ID = "extra_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val NAME_SHOP_ITEM = ""
        private const val COUNT_SHOP_ITEM = ""

    /*    fun newIntentAddItem(ctx: Context): Intent {
            val intent = Intent(ctx, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(ctx: Context, id: Int): Intent {
            val intent = Intent(ctx, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }*/


    }
}