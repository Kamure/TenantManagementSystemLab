package com.example.tenantmanagementsystemlab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystemlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastTenant: Tenant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val loggedEmail =
            intent.getStringExtra("LOGGED_EMAIL")

        if (loggedEmail != null) {

            Toast.makeText(
                this,
                "Logged in as $loggedEmail",
                Toast.LENGTH_LONG
            ).show()
        }


        binding.saveButton.setOnClickListener {

            if (binding.tenantNameEditText.text.isEmpty()) {
                binding.tenantNameEditText.error = "Required"
                return@setOnClickListener
            }

            if (binding.phoneEditText.text.isEmpty()) {
                binding.phoneEditText.error = "Required"
                return@setOnClickListener
            }

            if (binding.rentEditText.text.isEmpty()) {
                binding.rentEditText.error = "Required"
                return@setOnClickListener
            }

            val name =
                binding.tenantNameEditText.text.toString()

            val phone =
                binding.phoneEditText.text.toString()

            val rent =
                binding.rentEditText.text.toString()

            val tenant = Tenant(
                name,
                phone,
                rent
            )

            binding.tenant = tenant

            lastTenant = tenant
        }

        binding.callButton.setOnClickListener {

            val tenant = lastTenant

            if (tenant == null) {

                Toast.makeText(
                    this,
                    "Save a tenant first",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${tenant.phone}")
            )

            startActivity(intent)
        }
        binding.shareButton.setOnClickListener {

            val tenant = lastTenant

            if (tenant == null) {

                Toast.makeText(
                    this,
                    "Save a tenant first",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SEND)

            intent.type = "text/plain"

            intent.putExtra(
                Intent.EXTRA_TEXT,
                tenant.summary()
            )

            startActivity(
                Intent.createChooser(
                    intent,
                    "Share tenant"
                )
            )
        }
    }
}