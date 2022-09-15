package com.example.studentmanakotlin.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.studentmanakotlin.Presenter.IPresenter
import com.example.studentmanakotlin.Presenter.PresenterImp
import com.example.studentmanakotlin.R
import com.example.studentmanakotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
     private  var mainBinding:ActivityMainBinding?=null
    private lateinit var nav : BottomNavigationView
    private lateinit var homeFragment: HomeFragment
    private lateinit var container:FrameLayout
    private lateinit var transaction:FragmentTransaction
    companion object {
        var presenter:IPresenter = PresenterImp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding!!.root)
        anhXa()
        // when start
        replaceFragment(homeFragment)



        nav.setOnItemReselectedListener { item ->
            run {
                when (item.itemId) {
                    R.id.home -> {
                        homeFragment = HomeFragment()
                        replaceFragment(homeFragment)
                    }
                }

            }
        }
    }

    private fun anhXa(){
        // fragment
        homeFragment = HomeFragment()
        //view
        container = mainBinding!!.container
        nav = mainBinding!!.bottomNavigationView
        presenter.open()
    }

    private fun replaceFragment(fragment:Fragment){
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainBinding = null
    }



}