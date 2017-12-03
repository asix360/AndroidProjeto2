package com.example.info.ndexemple

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import com.example.info.ndexemple.Adapters.UnidadeAdapter
import com.example.info.ndexemple.Model.UnidadeSaude
import com.example.info.ndexemple.Util.SessionController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.unidades.view.*


@Suppress("DEPRECATION")
class NddrawActivity:AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var unidadeLista: MutableList<UnidadeSaude>;
    lateinit var  ref : DatabaseReference
    lateinit var listView : ListView
    var btnAdd: Button? = null



protected override fun onCreate(savedInstanceState:Bundle?){

    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_nddraw)
    val toolbar = findViewById(R.id.toolbar) as Toolbar
    setSupportActionBar(toolbar)

    IsLogin(SessionController.loginStatus)

    val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
    val toggle = ActionBarDrawerToggle(
   this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer.setDrawerListener(toggle)
    toggle.syncState()





    val navigationView = findViewById(R.id.nav_view) as NavigationView
    navigationView.setNavigationItemSelectedListener(this)




    ref = FirebaseDatabase.getInstance().getReference("UnidadeDeSaude");
    unidadeLista = mutableListOf();
    listView = findViewById(R.id.listViewUnidade) as ListView

    ref.addValueEventListener(object : ValueEventListener{

     override fun onCancelled(p0: DatabaseError?) {
         TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
     }

     override fun onDataChange(p0: DataSnapshot?) {
            if (p0!!.exists()){
                    unidadeLista.clear()
                    for ( h in p0.children){
                       var unidade = h.getValue(UnidadeSaude::class.java);
                        unidadeLista.add(unidade!!)
                    }
                val adapter = UnidadeAdapter(this@NddrawActivity, R.layout.unidades, unidadeLista)
                listView.adapter = adapter;
            }
     }




 });






}





public override fun onBackPressed() {
val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
if (drawer.isDrawerOpen(GravityCompat.START))
{
drawer.closeDrawer(GravityCompat.START)
}
else
{
super.onBackPressed()
}
}

public override fun onCreateOptionsMenu(menu:Menu):Boolean {
 // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nddraw, menu)
return true
}

public override fun onOptionsItemSelected(item:MenuItem):Boolean {

        val id = item.getItemId()
        if (id == R.id.action_settings)
{
return true
}

return super.onOptionsItemSelected(item)
}




    fun openInfoScreen(v : View){

            startActivity(Intent(this@NddrawActivity,UnidadeInfo::class.java))
    }


    fun openloginScreen(v: View){


        startActivity(Intent(this@NddrawActivity, LoginFirebaseActivity::class.java))

    }

    fun openaddScreen(v: View){

        startActivity(Intent(this@NddrawActivity, AddActivity::class.java))
    }


    private fun IsLogin(isLogin: Boolean){
               if (isLogin)
                   startActivity(Intent(this@NddrawActivity,LoginFirebaseActivity::class.java))

            }


        }











