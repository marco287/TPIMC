package com.example.tpimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;


    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";
    public String interpreteIMC(float imc){
        if (imc<=16.5)  return "famine";

        else if((imc>=16.5) && (imc<18.5)) return "maigreur";

        else if((imc>=18.5) && (imc<25)) return "corpulence normale";

        else if((imc>=25) && (imc<30)) return "surpoids";

        else if( (imc>=30) && (imc<35)) return "obésité modérée";

        else if ((imc<=35 && imc<=40) ) return "obésité sévère";

        else if (imc>40) return "Obésité très sévère";
        else return "";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// On récupère toutes les vues dont on a besoin
        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);

        // on associe aux vues des actions de clique
        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);

    }

    //Définition de l'action du clique du bouton envoyer
    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  on récupère la taille
            String t = taille.getText().toString();
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);

            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, "La taille doit être positive", Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if(pValue <= 0)
                    Toast.makeText(MainActivity.this, "Le poids doit etre positif", Toast.LENGTH_SHORT).show();
                else {
                    //Exercice 2 (une façon faire)
                    //if (t.contains(".")||t.contains(",")) group.check(R.id.radio_metre);
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat="Votre IMC est " + imc+" . ";

                    //Exercice 1 ( qui interprète l'IMC dans le résultat)

                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);


                    result.setText(resultat);
                }
            }
        }
    };


    //Définition de l'action du clique du bouton Reset
    private View.OnClickListener resetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };



    private View.OnClickListener checkedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };



    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);
            // (Exercice 2: Le bouton radio Mètre est coché automatiquement lorsqu'une virgule est détectée dans la taille)
            if (!s.toString().isEmpty()) {
                if(s.toString().charAt(s.length() - 1) == '.') {
                    RadioButton mRadio = findViewById(R.id.radio_metre);
                    mRadio.setChecked(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}