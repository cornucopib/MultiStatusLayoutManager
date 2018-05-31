package com.ivan.multistatuslayoutmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.ivan.multistatuslayoutmanager.core.MultiStatusLayoutManager
import com.ivan.multistatuslayoutmanager.core.StatusContainer
import com.ivan.multistatuslayoutmanager.core.SwitchStatusLayoutManager

class MainActivity : AppCompatActivity() {

    private var  m:SwitchStatusLayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        m= MultiStatusLayoutManager.getInstance().register(this).withNoNetWorkClickListener {
            showToast()
        }
    }

    private fun showToast(){
        Toast.makeText(this,"无网络重试",Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_multi_status_layout_demo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.loading_status_id -> m!!.switchTo(StatusContainer.LOADING_STATUS)
            R.id.no_network_id -> m!!.switchTo(StatusContainer.NO_NETWORK_STATUS)
            R.id.error_view_id ->  m!!.switchTo(StatusContainer.ERROR_STATUS)
            R.id.empty_view_id ->  m!!.switchTo(StatusContainer.EMPTY_STATUS)
            R.id.content_view_id ->  m!!.switchTo(StatusContainer.CONTENT_STATUS)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        m!!.release()
    }


}
