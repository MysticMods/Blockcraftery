package epicsquid.blockcraftery.block;

import epicsquid.blockcraftery.tile.TileEditableBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EditableBlockColors implements IBlockColor {

  @Override
  public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
    if (state.getBlock() instanceof IEditableBlock) {
      TileEntity t = worldIn.getTileEntity(pos);
      if (t instanceof TileEditableBlock) {
        IBlockState containedState = ((TileEditableBlock) t).state;
        return Minecraft.getMinecraft().getBlockColors().colorMultiplier(containedState, worldIn, pos, tintIndex);
      }
    }
    return 0xFFFFFF;
  }

}
