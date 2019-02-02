package br.com.loginphp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class InvoiceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        ExpandableListView elvCompra = (ExpandableListView) findViewById(R.id.elvCompra);

        // cria os grupos
        List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Doces");
        lstGrupos.add("Legumes");


        // cria os itens de cada grupo
        List<Address> lstDoces = new ArrayList<>();
        lstDoces.add(new Address("OPPA", "OOOOOO"));


        List<Address> lstLegumes = new ArrayList<>();
        lstLegumes.add(new Address("OPPAfff", "OOOOOO"));


        // cria o "relacionamento" dos grupos com seus itens
        HashMap<String, List<Address>> lstItensGrupo = new HashMap<>();
        lstItensGrupo.put(lstGrupos.get(0), lstDoces);
        lstItensGrupo.put(lstGrupos.get(1), lstLegumes);


        // cria um adaptador (BaseExpandableListAdapter) com os dados acima
        InvoiceAdapter adaptador = new InvoiceAdapter(this, lstGrupos, lstItensGrupo);
        // define o apadtador do ExpandableListView
        elvCompra.setAdapter(adaptador);
        elvCompra.
    }
}
