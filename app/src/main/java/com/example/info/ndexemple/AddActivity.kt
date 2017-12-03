package com.example.info.ndexemple

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.info.ndexemple.Model.UnidadeSaude
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.view.*

class AddActivity : AppCompatActivity() {

    lateinit var editTextNome : EditText;
    lateinit var editTextRua : EditText;
    lateinit var editTextnumero: EditText;
    lateinit var save: Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editTextNome = findViewById(R.id.editTextNome) as EditText;
        editTextRua = findViewById(R.id.editRua) as EditText
        editTextnumero = findViewById(R.id.editNumero) as EditText
        save  = findViewById(R.id.save) as Button


        save.setOnClickListener {
            SaveUnidade()
        }

    }

private fun SaveUnidade() {

        val nome = editTextNome.text.toString().trim();

            if (nome.isEmpty()){
                editTextNome.error = "Este campo não pode ficar vazio";
                return;
            }


        val ref = FirebaseDatabase.getInstance().getReference("UnidadeDeSaude")
        val idUnidade =  ref.push().key;
        val unidade = UnidadeSaude(idUnidade,nome,editTextRua.toString(),editTextnumero.toString(),0,0,0);
        ref.child(idUnidade).setValue(unidade).addOnCompleteListener {
            Toast.makeText(applicationContext,"Um nova unidade de Saude foi adicionada",Toast.LENGTH_LONG).show() }
    startActivity(Intent(this@AddActivity,NddrawActivity::class.java))
    }









}


