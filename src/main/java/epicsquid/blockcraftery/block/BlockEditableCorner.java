package epicsquid.blockcraftery.block;

import javax.annotation.Nonnull;

import epicsquid.blockcraftery.model.BakedModelEditableInnerCorner;
import epicsquid.blockcraftery.model.BakedModelEditableOuterCorner;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.block.BlockCornerBase;
import epicsquid.mysticallib.block.BlockTECornerBase;
import epicsquid.mysticallib.model.block.BakedModelBlock;
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

import static epicsquid.blockcraftery.block.BlockEditableCube.LIGHT;

public class BlockEditableCorner extends BlockTECornerBase implements IEditableBlock {

  public BlockEditableCorner(@Nonnull IBlockState state, @Nonnull SoundType type, float hardness, @Nonnull String name, boolean inner,
      @Nonnull Class<? extends TileEntity> teClass) {
    super(state, type, hardness, name, inner, teClass);
    setModelCustom(true);
    setLightOpacity(0);
    setOpacity(false);
    setDefaultState(blockState.getBaseState().withProperty(LIGHT, false));
  }

  @Override
  public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
    if (getParentState() != null) {
      return super.getLightOpacity(state, world, pos);
    }
    return super.getLightOpacity(state, world, pos);
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIGHT) ? 15 : 0;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
    return true;
  }

  @Override
  @Nonnull
  public BlockStateContainer createBlockState() {
    IProperty[] listedProperties = new IProperty[] { BlockCornerBase.INNER, BlockCornerBase.UP, BlockCornerBase.DIR, LIGHT };
    IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { STATEPROP };
    return new ExtendedBlockState(this, listedProperties, unlistedProperties);
  }

  @Override
  @Nonnull
  public IBlockState getExtendedState(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    TileEntity t = world.getTileEntity(pos);
    IBlockState actual = getActualState(state, world, pos).withProperty(INNER, inner);
    if (t instanceof TileEditableBlock && actual instanceof IExtendedBlockState) {
      return ((IExtendedBlockState) actual).withProperty(STATEPROP, ((TileEditableBlock) t).state);
    }
    return state;
  }

  private static final UnlistedPropertyState STATEPROP = new UnlistedPropertyState();

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
  public boolean doesSideBlockRendering(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof TileEditableBlock) {
      return ((TileEditableBlock) t).state.getBlock().doesSideBlockRendering(((TileEditableBlock) t).state, world, pos, side) && super
          .doesSideBlockRendering(state, world, pos, side);
    }
    return false;
  }

  @Override
  public boolean isSideSolid(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    return false;
  }

  @Override
  @Nonnull
  public IUnlistedProperty<IBlockState> getStateProperty() {
    return STATEPROP;
  }

  @Nonnull
  @Override
  protected Class<? extends BakedModelBlock> getModelClass(int type) {
    if (type == 1) {
      return BakedModelEditableOuterCorner.class;
    } else {
      return BakedModelEditableInnerCorner.class;
    }
  }
}
