package epicsquid.blockcraftery.block;

import epicsquid.blockcraftery.model.BakedModelEditableCube;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.block.BlockTEBase;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
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
import net.minecraft.util.ResourceLocation;
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

  public BlockEditableCube(Material mat, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    this.hasCustomModel = true;
    this.setLightOpacity(0);
    setOpacity(false);
    setDefaultState(blockState.getBaseState().withProperty(FULLCUBE, true).withProperty(OPAQUECUBE, false));
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
    return true;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return state.getValue(OPAQUECUBE);
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return !state.getValue(FULLCUBE);
  }

  @Override
  public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
    return true;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
    return BlockFaceShape.SOLID;
  }

  @Override
  public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
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
  public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof TileEditableBlock) {
      return ((TileEditableBlock) t).state.getBlock().doesSideBlockRendering(((TileEditableBlock) t).state, world, pos, side);
    }
    return true;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    IProperty[] listedProperties = new IProperty[] { FULLCUBE, OPAQUECUBE };
    IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { STATEPROP };
    return new ExtendedBlockState(this, listedProperties, unlistedProperties);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(FULLCUBE, meta % 2 == 1).withProperty(OPAQUECUBE, meta > 1);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 2 * (state.getValue(OPAQUECUBE) ? 1 : 0) + (state.getValue(FULLCUBE) ? 1 : 0);
  }

  @Override
  public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
    TileEntity t = world.getTileEntity(pos);
    IBlockState actual = getActualState(state, world, pos);
    if (t instanceof TileEditableBlock && actual instanceof IExtendedBlockState) {
      IBlockState tileState = ((TileEditableBlock) t).state;
      IBlockState placeState = tileState.getBlock().getActualState(tileState, world, pos);
      return ((IExtendedBlockState) actual).withProperty(STATEPROP, placeState);
    }
    return state;
  }

  public static final UnlistedPropertyState STATEPROP = new UnlistedPropertyState();

  public static class UnlistedPropertyState implements IUnlistedProperty<IBlockState> {
    @Override
    public String getName() {
      return "stateprop";
    }

    @Override
    public boolean isValid(IBlockState value) {
      return true;
    }

    @Override
    public Class<IBlockState> getType() {
      return IBlockState.class;
    }

    @Override
    public String valueToString(IBlockState value) {
      return value.toString();
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (this.hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(getRegistryName().getResourceDomain() + ":blocks/" + getRegistryName().getResourcePath());
      CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + name),
          new CustomModelBlock(BakedModelEditableCube.class, defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + name + "#inventory"),
          new CustomModelBlock(BakedModelEditableCube.class, defaultTex, defaultTex));
    }
  }

  @Override
  public IUnlistedProperty<IBlockState> getStateProperty() {
    return STATEPROP;
  }



}
