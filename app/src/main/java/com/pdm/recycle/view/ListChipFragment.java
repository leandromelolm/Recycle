package com.pdm.recycle.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.pdm.recycle.R;


public class ListChipFragment extends Fragment {

    private Chip chipPlastico,
            chipPapel,
            chipPapelao,
            chipMetal,
            chipOrganico,
            chipPilha,
            chipBateria,
            chipEletronico,
            chipOleoCozinha,
            chipOutroResiduo,
            chipResiduoColetado,
            chipResiduoNaoEncontrado,
            chipResiduoDescartadoHoje;
    private ChipGroup chipGroup;

    //private ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_list_search_option, container, false);
        View v = inflater.inflate(R.layout.fragment_list_chips, container, false);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //View vv = binding.getRoot();

        chipPlastico      = v.findViewById(R.id.chipPlastico);
        chipPapel         = v.findViewById(R.id.chipPapel);
        chipMetal         = v.findViewById(R.id.chipMetal);
        chipOrganico      = v.findViewById(R.id.chipOrganico);
        chipPilha         = v.findViewById(R.id.chipPilha);
        chipBateria       = v.findViewById(R.id.chipBateria);
        chipEletronico    = v.findViewById(R.id.chipEletronico);
        chipOleoCozinha   = v.findViewById(R.id.chipOleoCozinha);
        chipOutroResiduo  = v.findViewById(R.id.chipOutroResiduo);

        chipResiduoColetado       = v.findViewById(R.id.chipResiduoColetado);
        chipResiduoNaoEncontrado  = v.findViewById(R.id.chipResiduoNaoEncontrado);
        chipResiduoDescartadoHoje = v.findViewById(R.id.chipResiduoDescartadoHoje);

        String plasticoText     = chipPlastico.getText().toString();
        String papelText        = chipPapel.getText().toString();
        String metalText        = chipMetal.getText().toString();
        String organicoText     = chipOrganico.getText().toString();
        String pilhaText        = chipPilha.getText().toString();
        String bateriaText      = chipBateria.getText().toString();
        String eletronicoText   = chipEletronico.getText().toString();
        String oleoCozinhaText  = chipOleoCozinha.getText().toString();
        String outroResiduoText = chipOutroResiduo.getText().toString();

        String residuoColetadoText            = chipResiduoColetado.getText().toString();
        String residuoNaoEncontradoText       = chipResiduoNaoEncontrado.getText().toString();
        String residuoDescartadoHojeText      = chipResiduoDescartadoHoje.getText().toString();


        chipGroup = v.findViewById(R.id.chipGroup);


        /* Capturando o evento de seleção do chip */

        /* tipo de residuo descartado */
       chipPlastico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipPlastico.isChecked()){
                    //Toast.makeText(getActivity(), "Plastico marcado", Toast.LENGTH_SHORT).show();
                    Log.i("chipPlastico", " Plastico marcado");
                    ((MainHomeActivity) getActivity()).checkChip(plasticoText);

                }else if(!chipPlastico.isChecked() && !chipPapel.isChecked()){
                    //Toast.makeText(getActivity(), "Plastico desmarcado", Toast.LENGTH_SHORT).show();
                    Log.i("chipPlastico", " Plástico desmarcardo");
                    ((MainHomeActivity) getActivity()).checkChip("todos");
                }
            }
        });
        chipPapel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipPapel.isChecked()){
                    Log.i("chipPapel", " Papel marcado");
                    ((MainHomeActivity) getActivity()).checkChip(papelText);
                }else{
                    Log.i("chipPapel", " Papel desmarcardo");
                }
            }
        });
        chipMetal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipMetal.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(metalText);
                }
            }
        });
        chipOrganico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipOrganico.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(organicoText);
                }
            }
        });
        chipPilha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipPilha.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(pilhaText);
                }
            }
        });
        chipBateria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipBateria.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(bateriaText);
                }
            }
        });
        chipEletronico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipEletronico.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(eletronicoText);
                }
            }
        });
        chipOleoCozinha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipOleoCozinha.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(oleoCozinhaText);
                }
            }
        });
        chipOutroResiduo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipOutroResiduo.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(outroResiduoText);
                }
            }
        });

        //* status do residuos  */

        chipResiduoColetado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipResiduoColetado.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(residuoColetadoText);
                }
            }
        });
        chipResiduoNaoEncontrado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipResiduoNaoEncontrado.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(residuoNaoEncontradoText);
                }
            }
        });
        chipResiduoDescartadoHoje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chipResiduoDescartadoHoje.isChecked()){
                    ((MainHomeActivity) getActivity()).checkChip(residuoDescartadoHojeText);
                }
            }
        });

/*
        //Implementação usando o chipGroup para verificar seleção
        // essa implementação diminui a quantidade de linhas
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip marcado = chipGroup.findViewById(checkedId);

                if(marcado.getText().equals("plástico")){
                    Log.i("chipPlastico", " plástico marcado");
                    ((MainHomeActivity) getActivity()).checkChip(plasticoText);
                }else if(marcado.getText().equals("papel")){
                    Log.i("chipPapel", " papel marcado");
                    ((MainHomeActivity) getActivity()).checkChip(papelText);
                }else if(marcado.getText().equals("metal")){
                Log.i("chipMetal", " metal marcado");
                ((MainHomeActivity) getActivity()).checkChip(metalText);
                }else if(marcado.getText().equals("orgânico")){
                    Log.i("chipOrganico", " orgânico marcado");
                    ((MainHomeActivity) getActivity()).checkChip(organicoText);
                }
                //continuar implementação
            }
        });
*/
        return v;
    }
}