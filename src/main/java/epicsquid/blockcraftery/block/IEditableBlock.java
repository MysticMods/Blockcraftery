package epicsquid.blockcraftery.block;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public interface IEditableBlock {
  public IUnlistedProperty<IBlockState> getStateProperty();
}
