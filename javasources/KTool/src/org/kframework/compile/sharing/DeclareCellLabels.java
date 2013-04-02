package org.kframework.compile.sharing;

import org.kframework.compile.utils.BasicCompilerStep;
import org.kframework.kil.Module;
import org.kframework.kil.Definition;
import org.kframework.kil.Terminal;

/**
 * Generate a sort CellLabel with a constant for each cell label in the configuration.
 * Must run after FlattenModules
 */
public class DeclareCellLabels extends BasicCompilerStep<Definition> {
	@Override
	public String getName() {
		return "Generate CellLabel sort";
	}
		
	@Override
	public Definition compile(Definition def, String stepName) {
		Module module = def.getSingletonModule();		

		CellLabelCollector labels = new CellLabelCollector();
		module.accept(labels);
		
		for (String cellLabel : labels.cellLabels) {
			module.addProduction("CellLabel", new Terminal(cellLabel));
		}
		
		return def;
	}
}