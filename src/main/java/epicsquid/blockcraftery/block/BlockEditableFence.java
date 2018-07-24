package epicsquid.blockcraftery.block;

import javax.annotation.Nonnull;

import epicsquid.blockcraftery.model.BakedModelEditableFence;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.block.BlockTEFenceBase;
import epicsquid.mysticallib.model.block.BakedModelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEditableFence extends BlockTEFenceBase implements IEditableBlock {

  public BlockEditableFence(@Nonnull Block block, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nonnull Class<? extends TileEntity> teClass) {
    super(block, type, hardness, name, teClass);
    setModelCustom(true);
    setLightOpacity(0);
    setOpacity(false);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
    return true;
  }

  @Override
  @Nonnull
  protected BlockStateContainer createBlockState() {
    IProperty[] listedProperties = new IProperty[] { NORTH, SOUTH, WEST, EAST };
    IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { STATEPROP };
    return new ExtendedBlockState(this, listedProperties, unlistedProperties);
  }

  @Override
  @Nonnull
  public IBlockState getExtendedState(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    TileEntity t = world.getTileEntity(pos);
    IBlockState actual = getActualState(state, world, pos);
    if (t instanceof TileEditableBlock && actual instanceof IExtendedBlockState) {
      return ((IExtendedBlockState) actual).withProperty(STATEPROP, ((TileEditableBlock) t).state);
    }
    return state;
  }

  private static final BlockEditableWall.UnlistedPropertyState STATEPROP = new BlockEditableWall.UnlistedPropertyState();

  public static class UnlistedPropertyState implements IUnlistedProperty<IBlockState> {

    @Override
    @Nonnull
    public String getName() {
      return "stateprop";
    }

    @Override
    public boolean isValid(@Nonnull IBlockState value) {
      return true;
    }

    @Override
    @Nonnull
    public Class<IBlockState> getType() {
      return IBlockState.class;
    }

    @Override
    @Nonnull
    public String valueToString(@Nonnull IBlockState value) {
      return value.toString();
    }
  }

  @Override
  public boolean shouldSideBeRendered(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    return true;
  }

  @Override
  public boolean doesSideBlockRendering(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    return false;
  }

  @Override
  public boolean isSideSolid(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    return false;
  }

  @Nonnull
  @Override
  protected Class<? extends BakedModelBlock> getModelClass() {
    return BakedModelEditableFence.class;
  }

  @Override
  public IUnlistedProperty<IBlockState> getStateProperty() {
    return STATEPROP;
  }

}
