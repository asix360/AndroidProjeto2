package com.example.info.ndexemple.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.info.ndexemple.Model.UnidadeSaude
import com.example.info.ndexemple.R
import com.example.info.ndexemple.UnidadeInfo
import com.example.info.ndexemple.Util.SessionController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_unidade_info.view.*
import kotlinx.android.synthetic.main.unidades.view.*
import java.util.*

/**
 * Created by Lessandro on 28/11/2017.
 */
class UnidadeAdapter(private val mContext: Context, private val layoutResid: Int, private val unidadelist:List<UnidadeSaude>)
            :ArrayAdapter<UnidadeSaude>(mContext,layoutResid,unidadelist){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view : View = layoutInflater.inflate(layoutResid, null)
        val textViewName =  view.findViewById(R.id.textViewName);
        val ranting = view.findViewById(R.id.votosUnidade)

        val unidade =  unidadelist[position];
        textViewName.textViewName.text = unidade.nome;
        ranting.votosUnidade.rating = unidade.voto.toFloat()


        view.setOnClickListener {
            showWindowsVoto(unidade)
        }



        return view;
    }




    private fun showWindowsVoto(unidade: UnidadeSaude) {
        val contrutor = AlertDialog.Builder(mContext);
        val inflater =  LayoutInflater.from(mContext);
        val view = inflater.inflate(R.layout.activity_unidade_info, null)
        val editText = view.findViewById(R.id.tvNomeInf)
        val statvotoedit = view.findViewById(R.id.ratingBarInf)


        editText.tvNomeInf.text = unidade.nome;
        statvotoedit.ratingBarInf.rating = unidade.voto.toFloat();

        contrutor.setView(view)


        contrutor.setPositiveButton("Sim") { dialog, which ->

            val dbUnidade = FirebaseDatabase.getInstance().getReference("UnidadeDeSaude")
            val name = editText.tvNomeInf.toString().trim()
            if (name.isEmpty()){

                return@setPositiveButton
            }


            val unidadeTemp = UnidadeSaude(unidade.id,unidade.nome,unidade.rua,unidade.numero,statvotoedit.ratingBarInf.rating.toInt(),0,0)

            dbUnidade.child(unidade.id).setValue(unidadeTemp);
            Toast.makeText(mContext,"Voto Salvo com sucesso",Toast.LENGTH_LONG);

        }

        contrutor.setNegativeButton("nâo") { dialog, which ->



        }

        val alerta = contrutor.create();
        alerta.show();
        

    }


}