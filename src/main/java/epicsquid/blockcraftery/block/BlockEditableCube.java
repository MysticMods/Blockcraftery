package epicsquid.blockcraftery.block;

import javax.annotation.Nonnull;

import epicsquid.blockcraftery.model.BakedModelEditableCube;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.block.BlockTEBase;
import epicsquid.mysticallib.model.block.BakedModelBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
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

public class BlockEditableCube extends BlockTEBase implements IEditableBlock {

  public static final PropertyBool FULLCUBE = PropertyBool.create("fullcube");
  public static final PropertyBool OPAQUECUBE = PropertyBool.create("opaquecube");
  public static final PropertyBool LIGHT = PropertyBool.create("light");

  public BlockEditableCube(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nonnull Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    setModelCustom(true);
    this.setLightOpacity(0);
    setOpacity(false);
    setDefaultState(blockState.getBaseState().withProperty(FULLCUBE, true).withProperty(OPAQUECUBE, false).withProperty(LIGHT, false));
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean canRenderInLayer(@Nonnull IBlockState state, @Nonnull BlockRenderLayer layer) {
    return true;
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return state.getValue(OPAQUECUBE);
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return !state.getValue(FULLCUBE);
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIGHT) ? 15 : 0;
  }

  @Override
  public boolean isLadder(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entity) {
    return true;
  }

  @Override
  @Nonnull
  public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess blockAccess, @Nonnull IBlockState blockState, @Nonnull BlockPos pos, @Nonnull EnumFacing facing) {
    return BlockFaceShape.SOLID;
  }

  @Override
  public boolean shouldSideBeRendered(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    IBlockState s = world.getBlockState(pos.offset(side));
    if (s.getBlock() == this) {
      TileEntity t = world.getTileEntity(pos);
      TileEntity t2 = world.getTileEntity(pos.offset(side));
      if (t instanceof TileEditableBlock && t2 instanceof TileEditableBlock && ((TileEditableBlock) t).state.getBlock() == ((TileEditableBlock) t2).state
          .getBlock() && ((TileEditableBlock) t).state.getBlock() != Blocks.AIR) {
        return false;
      }
    }
    return super.shouldSideBeRendered(state, world, pos, side);
  }

  @Override
  public boolean doesSideBlockRendering(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof TileEditableBlock) {
      return ((TileEditableBlock) t).state.getBlock().doesSideBlockRendering(((TileEditableBlock) t).state, world, pos, side);
    }
    return true;
  }

  @Override
  @Nonnull
  protected BlockStateContainer createBlockState() {
    IProperty[] listedProperties = new IProperty[] { FULLCUBE, OPAQUECUBE, LIGHT };
    IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { STATEPROP };
    return new ExtendedBlockState(this, listedProperties, unlistedProperties);
  }

  @Override
  @Nonnull
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(LIGHT, meta << 2 == 1).withProperty(OPAQUECUBE, meta << 1 == 1).withProperty(LIGHT, meta == 1);
  }

  @Override
  public int getMetaFromState(@Nonnull IBlockState state) {
    return (state.getValue(LIGHT) ? 1 : 0) >> 2 + (state.getValue(OPAQUECUBE) ? 1 : 0) >> 1 + (state.getValue(FULLCUBE) ? 1 : 0);
  }

  @Override
  @Nonnull
  public IBlockState getExtendedState(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    TileEntity t = world.getTileEntity(pos);
    IBlockState actual = getActualState(state, world, pos);
    if (t instanceof TileEditableBlock && actual instanceof IExtendedBlockState) {
      IBlockState tileState = ((TileEditableBlock) t).state;
      IBlockState placeState = tileState.getBlock().getActualState(tileState, world, pos);
      return ((IExtendedBlockState) actual).withProperty(STATEPROP, placeState);
    }
    return state;
  }

  @Override
  public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
    if (getParentState() != null) {
      return super.getLightOpacity(getParentState(), world, pos);
    }
    return super.getLightOpacity(state, world, pos);
  }

  public static final UnlistedPropertyState STATEPROP = new UnlistedPropertyState();

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
  @Nonnull
  public IUnlistedProperty<IBlockState> getStateProperty() {
    return STATEPROP;
  }

  @Nonnull
  @Override
  protected Class<? extends BakedModelBlock> getModelClass() {
    return BakedModelEditableCube.class;
  }
}
