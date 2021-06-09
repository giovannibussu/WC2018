package worldcup.impl.converter;

import java.util.ArrayList;
import java.util.List;

import worldcup.model.Grafico;
import worldcup.model.Distribuzione;

public class GraficoConverter {

	public static Grafico toRsModelGrafico(List<worldcup.business.calculator.Distribuzione> dto) {
		Grafico rsModel = new Grafico();

		rsModel.setClickItemLegenda(false);
		rsModel.setColonneLegenda(16);
		rsModel.setColoriAutomatici(true);
		rsModel.setLimiteColonneLegenda(16);
		rsModel.setValoreRealeTorta(true);
		rsModel.setxAxisGridLines(true);
		
		rsModel.setDati(toRsModel(dto));
		
		return rsModel;
	}
	
	private static List<Distribuzione> toRsModel(List<worldcup.business.calculator.Distribuzione> dto) {
		List<Distribuzione> rsModelS = null;
		
		if(dto != null) {
			rsModelS = new ArrayList<Distribuzione>();
			
			for (worldcup.business.calculator.Distribuzione dDto : dto) {
				Distribuzione dRsModel = new Distribuzione();
				
				dRsModel.setNome(dDto.getLabel());
				dRsModel.setTooltip(dDto.getTooltip());
				dRsModel.setValore(dDto.getValue());
				
				rsModelS.add(dRsModel);
			}
		}
		
		return rsModelS;
		
	}
}
