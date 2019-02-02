package br.com.loginphp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class InvoiceAdapter extends  BaseExpandableListAdapter {

    private List<String> lstGrupos;
    private HashMap<String, List<Address>> lstItensGrupos;
    private Context context;

    public InvoiceAdapter(Context context, List<String> grupos, HashMap<String, List<Address>> itensGrupos) {
        this.context = context;
        lstGrupos = grupos;
        lstItensGrupos = itensGrupos;
    }

    @Override
    public int getGroupCount() {
        // retorna a quantidade de grupos
        return lstGrupos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // retorna a quantidade de itens de um grupo
        return lstItensGrupos.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // retorna um grupo
        return lstGrupos.get(groupPosition);
    }

    @Override
    public Address getChild(int groupPosition, int childPosition) {
        return lstItensGrupos.get(getGroup(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo, null);
        }

        TextView tvGrupo = (TextView) convertView.findViewById(R.id.tvGrupo);


        tvGrupo.setText((String) getGroup(groupPosition));


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grupo, null);
        }

        TextView tvItem = convertView.findViewById(R.id.tvItem);
        TextView TV = convertView.findViewById(R.id.tvValor);

        Address address = getChild(groupPosition, childPosition);
        tvItem.setText(address.getReference());
        TV.setText(address.getUrl());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}