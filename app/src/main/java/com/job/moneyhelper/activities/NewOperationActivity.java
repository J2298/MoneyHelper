package com.job.moneyhelper.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.job.moneyhelper.models.Operacion;
import com.job.moneyhelper.R;
import com.job.moneyhelper.repositories.Repository;

public class NewOperationActivity extends AppCompatActivity {

    private EditText monto;
    private RadioGroup tipo;
    private Spinner cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);

        monto = (EditText)findViewById(R.id.monto);
        tipo = (RadioGroup)findViewById(R.id.rg);
        cuenta = (Spinner)findViewById(R.id.tiposCuentas);
    }

    public void registrar(View view){
        String montoI = monto.getText().toString();
        String tipoI = ((RadioButton)findViewById(tipo.getCheckedRadioButtonId())).getText().toString();
        String cuentaI = (String)cuenta.getSelectedItem();



        if(montoI.isEmpty()) {
            Toast.makeText(this, "Complete campo vacio", Toast.LENGTH_SHORT).show();
            return;
        }



        Operacion ope1= new Operacion(Double.parseDouble(montoI),tipoI,cuentaI);

        Repository opeRepository = Repository.getInstance();
        opeRepository.agregarOperacion(ope1);

        finish();
    }
}






