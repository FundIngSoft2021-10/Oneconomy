package src.Controler;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView textCell;
    private int indexC;
    private int indexR;

    public TableDynamic(TableLayout tableLayout,Context context){
        this.tableLayout = tableLayout;
        this.context = context;
    }
    public void addData (ArrayList<String[]> data,String[] header){
        this.data=data;
        this.header=header;
        createDataTable();
    }
    private void newRow(){
        tableRow = new TableRow(context);
    }
    private void newCell(){
        textCell =new TextView(context);
        textCell.setGravity(Gravity.CENTER);
        textCell.setTextSize(25);
    }
    private void createHeader(){
        indexC=0;
        newRow();
        while(indexC<header.length){
            newCell();
            textCell.setText(header[indexC++]);
            tableRow.addView(textCell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }
    private void createDataTable(){
        tableLayout.removeAllViews();
        createHeader();
        String info;
        if(!data.isEmpty()){
            for(indexR=1;indexR<=data.size();indexR++){
                newRow();
                for (indexC=0;indexC<=header.length;indexC++){
                    newCell();
                    String[] columns = data.get(indexR-1);
                    info = (indexC<columns.length)?columns[indexC]:"";
                    textCell.setText(info);
                    tableRow.addView(textCell,newTableRowParams());
                }
                tableLayout.addView(tableRow);
            }
        }

    }


    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params= new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }




}





