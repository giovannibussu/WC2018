/**
 * 
 */
package worldcup.business.calculator;

import java.util.Random;

/**
 * @author Bussu Giovanni (bussu@link.it)
 * @author  $Author: bussu $
 * @version $ Rev: 12563 $, $Date: Jun 22, 2021 $
 * 
 */
public class RandomPerformanceEvaluator extends AbstractPerformanceEvaluator{

	private Random random = new Random();

	@Override
	protected Integer calculatePerformance(GironePerformance performance) {
		return random.nextInt();
	}
}
