package com.example.dsadsa;


import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.models.Notas;

import databaseModels.NotasBD;

public class NotasPrincipal extends Activity {

	String curso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notas_principal);
		Bundle bolsa = getIntent().getExtras();
		setTitle(bolsa.getString("CURSO"));
		curso = bolsa.getString("CURSO");
		TableLayout tl = (TableLayout) findViewById(R.id.main_table);
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		TextView label_date = new TextView(this);
        label_date.setId(2);
        label_date.setText("NOMBRE");
        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        tr_head.addView(label_date);
        
        TextView label_detalle_kg = new TextView(this);
        label_detalle_kg.setId(4);
        label_detalle_kg.setText("FECHA");  
        label_detalle_kg.setTextColor(Color.WHITE);
        label_detalle_kg.setPadding(5, 5, 5, 5);
        tr_head.addView(label_detalle_kg);
        
        TextView label_establecimiento = new TextView(this);
        label_establecimiento.setId(5);
        label_establecimiento.setText("NOTA");  
        label_establecimiento.setTextColor(Color.WHITE);
        label_establecimiento.setPadding(5, 5, 5, 5); 
        tr_head.addView(label_establecimiento);
        
        TextView label_valor = new TextView(this);
        label_valor.setId(5);
        label_valor.setText("CORTE");  
        label_valor.setTextColor(Color.WHITE);
        label_valor.setPadding(5, 5, 5, 5); 
        tr_head.addView(label_valor); 
        
        tl.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        NotasBD nb = new NotasBD(NotasPrincipal.this);
		nb.abrir();
		int count =0;
		ArrayList<Notas> notas = nb.recibir();
		for(int i=0;i<notas.size();i++){
			TableRow tr = new TableRow(this);
			if(count % 2!=0){
				tr.setBackgroundColor(Color.GRAY);
			}else{
				tr.setBackgroundColor(Color.BLACK);
			}
				
			tr.setId(100+count);
			tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			Notas n = new Notas();
			n = notas.get(i);
			
			TextView label_detalle = new TextView(this);
			label_detalle.setId(200+count);
			label_detalle.setText(n.getNombreNota());
			label_detalle.setPadding(2, 0, 5, 0);
			label_detalle.setTextColor(Color.WHITE);
			tr.addView(label_detalle);
			
			TextView label_estab = new TextView(this);
			label_estab.setId(200+count);
			label_estab.setText(n.getFechaNota());
			label_estab.setPadding(2, 0, 5, 0);
			label_estab.setTextColor(Color.WHITE);
			tr.addView(label_estab);
			
			TextView label_value = new TextView(this);
			label_value.setId(200+count);
			label_value.setText(n.getNota());
			label_value.setPadding(2, 0, 5, 0);
			label_value.setTextColor(Color.WHITE);
			tr.addView(label_value);
			
			TextView label_fecha = new TextView(this);
			label_fecha.setId(200+count);
			label_fecha.setText(Integer.toString(n.getCorte()));
			label_fecha.setPadding(2, 0, 5, 0);
			label_fecha.setTextColor(Color.WHITE);
			tr.addView(label_fecha);
			
			tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			count++;
		}
		nb.cerrar();
		
	}
	
}