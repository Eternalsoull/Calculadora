package com.example.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.myapplication3.databinding.ActivityMainBinding
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding
    var number: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContent()

    }

    fun setContent() {
        binding.btnC.setOnClickListener(this)
        binding.btnUno.setOnClickListener(this)
        binding.btnDos.setOnClickListener(this)
        binding.btnTres.setOnClickListener(this)
        binding.btnCuatro.setOnClickListener(this)
        binding.btnCinco.setOnClickListener(this)
        binding.btnSeis.setOnClickListener(this)
        binding.btnSiete.setOnClickListener(this)
        binding.btnOcho.setOnClickListener(this)
        binding.btnNueve.setOnClickListener(this)
        binding.btnCero.setOnClickListener(this)
        binding.btnComa.setOnClickListener(this)
        binding.btnSuma.setOnClickListener(this)
        binding.btnResta.setOnClickListener(this)
        binding.btnMultiplicacion.setOnClickListener(this)
        binding.btnDividir.setOnClickListener(this)
        binding.btnIgual.setOnClickListener(this)
        binding.btnParentesisAbre.setOnClickListener(this)
        binding.btnParentesisCierra.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUno -> {
                binding.etNombrePersona.append("1")
            }

            R.id.btnDos -> {
                binding.etNombrePersona.append("2")
            }

            R.id.btnTres -> {
                binding.etNombrePersona.append("3")
            }

            R.id.btnCuatro -> {
                binding.etNombrePersona.append("4")
            }

            R.id.btnCinco -> {
                binding.etNombrePersona.append("5")
            }

            R.id.btnSeis -> {
                binding.etNombrePersona.append("6")
            }

            R.id.btnSiete -> {
                binding.etNombrePersona.append("7")
            }

            R.id.btnOcho -> {
                binding.etNombrePersona.append("8")
            }

            R.id.btnNueve -> {
                binding.etNombrePersona.append("9")
            }

            R.id.btnCero -> {
                binding.etNombrePersona.append("0")
            }

            R.id.btnComa -> {
                binding.etNombrePersona.append(".")
            }

            R.id.btnSuma -> {
                binding.etNombrePersona.append("+")
            }

            R.id.btnResta -> {
                binding.etNombrePersona.append("-")
            }

            R.id.btnMultiplicacion -> {
                binding.etNombrePersona.append("*")
            }

            R.id.btnDividir -> {
                binding.etNombrePersona.append("/")
            }

            R.id.btnC -> {
                //borrar de un caracter a la vez
                val cadena = binding.etNombrePersona.text.toString()
                if (cadena.isNotEmpty()) {
                    binding.etNombrePersona.setText(cadena.substring(0, cadena.length - 1))
                }
            }
        }

        when (v?.id) {
            R.id.btnIgual -> {
                val cadena = binding.etNombrePersona.text.toString() // Obtener la cadena de texto del EditText

                // Resolver las operaciones dentro de los paréntesis
                var expresionConParentesis = resolverParentesis(cadena)

                // Dividir la expresión en números y operadores
                val expresion: MutableList<String> = mutableListOf() // Lista que contendrá los números y operadores
                var num = ""
                for (caracter in expresionConParentesis) {
                    if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/') {
                        expresion.add(num)
                        expresion.add(caracter.toString())
                        num = ""
                    } else {
                        num += caracter
                    }
                }
                expresion.add(num)

                // Resolver primero las multiplicaciones y divisiones
                val expresionConMultiplicacionesYDivisiones: MutableList<String> = mutableListOf()
                var i = 0
                while (i < expresion.size) {
                    val token = expresion[i]
                    if (token == "*" || token == "/" || token == "(") {
                        val numAnterior = expresionConMultiplicacionesYDivisiones.removeAt(
                            expresionConMultiplicacionesYDivisiones.size - 1
                        ).toDouble()
                        val operacion = expresion[i]
                        val numSiguiente = expresion[i + 1].toDouble()
                        val resultadoOperacion =
                            if (operacion == "*") numAnterior * numSiguiente else numAnterior / numSiguiente
                        expresionConMultiplicacionesYDivisiones.add(resultadoOperacion.toString())
                        i += 2
                    } else {
                        expresionConMultiplicacionesYDivisiones.add(token)
                        i++
                    }
                }
                // Realizar las sumas y restas
                var resultado = expresionConMultiplicacionesYDivisiones[0].toDouble()
                i = 1
                while (i < expresionConMultiplicacionesYDivisiones.size) {
                    val operador = expresionConMultiplicacionesYDivisiones[i]
                    val numero = expresionConMultiplicacionesYDivisiones[i + 1].toDouble()
                    when (operador) {
                        "+" -> resultado += numero
                        "-" -> resultado -= numero
                    }
                    i += 2
                }

                // Mostrar el resultado en el EditText
                if (resultado % 1 == 0.0) {
                    binding.etNombrePersona.setText(resultado.toInt().toString())
                } else {
                    binding.etNombrePersona.setText(resultado.toString())
                }
            }

            R.id.btnParentesisAbre -> {
                binding.etNombrePersona.append("(")
            }

            R.id.btnParentesisCierra -> {
                binding.etNombrePersona.append(")")
            }
        }
    }
}

//funcion para resolver las operaciones dentro de los paréntesis
    fun resolverParentesis(cadena: String): String {
        val pila = mutableListOf<Int>()
        val parentesisAbre = '('
        val parentesisCierra = ')'
        for (i in cadena.indices) {
            if (cadena[i] == parentesisAbre) {
                pila.add(i)
            } else if (cadena[i] == parentesisCierra) {
                if (pila.isEmpty()) {
                    throw IllegalArgumentException("Error: hay un paréntesis que no se cierra")
                }
                val parentesisAbreIndex = pila.removeAt(pila.size - 1)
                val subcadena = cadena.substring(parentesisAbreIndex + 1, i)
                val resultadoSubcadena = resolverParentesis(subcadena) //
                val nuevaCadena =
                    cadena.substring(0, parentesisAbreIndex) + resultadoSubcadena + cadena.substring(i + 1)
                return resolverParentesis(nuevaCadena)
            }
        }

        if (pila.isNotEmpty()) {
            throw IllegalArgumentException("Error: hay un paréntesis que no se cierra")
        }
        return cadena
}

