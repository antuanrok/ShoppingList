package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.ActivityInterractor{

    private var mode: String = MODE_UNKNOWN
    private var id: Int = ShopItem.UNDEFINED_ID

    override fun onFragmentClosed() {
     finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if (savedInstanceState == null) {
            chooseRightRegim()
        }
    }



    private fun chooseRightRegim() {
        val fragment = when (mode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(id)
            else -> throw RuntimeException("Unknown screen mode $mode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id._shop_item_container,fragment)
            .commit()
    }


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