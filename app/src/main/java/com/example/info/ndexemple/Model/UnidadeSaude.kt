package com.example.info.ndexemple.Model

import android.widget.EditText


class UnidadeSaude(val id:String, val nome: String, val rua : String, val numero: String,val voto: Int, val latitude: Int, val logitude: Int) {

constructor() : this("","","","",0,0,0)
}
