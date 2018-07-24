package epicsquid.blockcraftery.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epicsquid.blockcraftery.model.BakedModelEditableCube;
import epicsquid.blockcraftery.model.BakedModelEditableSlab;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.block.BlockTESlabBase;
import epicsquid.mysticallib.model.block.BakedModelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

public class BlockEditableSlab extends BlockTESlabBase implements IEditableBlock {

  public BlockEditableSlab(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nonnull IBlockState parent, boolean isDouble,
      @Nullable Block slab, @Nonnull Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, parent, isDouble, slab, teClass);
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
    IProperty[] listedProperties = new IProperty[] { BlockSlab.HALF };
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

  private static final UnlistedPropertyState STATEPROP = new UnlistedPropertyState();

  public static class UnlistedPropertyState implements IUnlistedProperty<IBlockState> {

    @Override
    @Nonnull
    public String getName() {
      return "stateprop";
    }

    @Override
    public boolean isValid(IBlockState value) {
      return true;
    }

    @Override
    @Nonnull
    public Class<IBlockState> getType() {
      return IBlockState.class;
    }

    @Override
    @Nonnull
    public String valueToString(IBlockState value) {
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
  protected Class<? extends BakedModelBlock> getModelClass(int type) {
    if (type == 1) {
      return BakedModelEditableCube.class;
    } else {
      return BakedModelEditableSlab.class;
    }
  }

  @Override
  @Nonnull
  public IUnlistedProperty<IBlockState> getStateProperty() {
    return STATEPROP;
  }

}
