package me.aerclient.config.valuesystem;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Properties;

public class BlockArrayValue extends Value<ArrayList<Block>> {


    public BlockArrayValue(String name, String description, ArrayList<Block> values) {
        super(name, description, values);
    }

    @Override
    public void addToProperties(Properties props) {
        int cycle = 0;
        for (Block b : getObject()) {
            props.setProperty(this.getName() + cycle, "ID:" + Block.getIdFromBlock(b));
            cycle++;
        }
        props.setProperty(this.getName() + "-ArraySize", "" + cycle);

	}

	@Override
	public void fromProperties(Properties props) {
		ArrayList vals = new ArrayList<Block>();
		int size = Integer.parseInt(props.getProperty(this.getName() + "-ArraySize"));
		while (size > 0) {
			Block b = Block.getBlockById(Integer.parseInt(props.getProperty(this.getName() + size)));
			vals.add(b);
			size--;
		}

	}

}
