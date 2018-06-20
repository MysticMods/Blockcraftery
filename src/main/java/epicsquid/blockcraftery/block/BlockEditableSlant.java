package epicsquid.blockcraftery.block;

import epicsquid.blockcraftery.model.BakedModelEditableSlant;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.block.BlockSlantBase;
import epicsquid.mysticallib.block.BlockTESlantBase;
import epicsquid.mysticallib.model.CustomModelBlock;
import epicsquid.mysticallib.model.CustomModelLoader;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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

public class BlockEditableSlant extends BlockTESlantBase implements IEditableBlock {

  public BlockEditableSlant(IBlockState state, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(state, type, hardness, name, teClass);
    this.hasCustomModel = true;
    setLightOpacity(0);
    setOpacity(false);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
    return true;
  }

  @Override
  public BlockStateContainer createBlockState() {
    IProperty[] listedProperties = new IProperty[] { BlockSlantBase.VERT, BlockSlantBase.DIR };
    IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { STATEPROP };
    return new ExtendedBlockState(this, listedProperties, unlistedProperties);
  }

  @Override
  public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
    TileEntity t = world.getTileEntity(pos);
    IBlockState actual = getActualState(state, world, pos);
    if (t instanceof TileEditableBlock && actual instanceof IExtendedBlockState) {
      return ((IExtendedBlockState) actual).withProperty(STATEPROP, ((TileEditableBlock) t).state);
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
  public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof TileEditableBlock) {
      return ((TileEditableBlock) t).state.getBlock().doesSideBlockRendering(((TileEditableBlock) t).state, world, pos, side) && super
          .doesSideBlockRendering(state, world, pos, side);
    }
    return false;
  }

  @Override
  public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void initCustomModel() {
    if (this.hasCustomModel) {
      ResourceLocation defaultTex = new ResourceLocation(getRegistryName().getResourceDomain() + ":blocks/" + getRegistryName().getResourcePath());
      if (parent != null) {
        defaultTex = new ResourceLocation(
            parent.getBlock().getRegistryName().getResourceDomain() + ":blocks/" + parent.getBlock().getRegistryName().getResourcePath());
      }
      CustomModelLoader.blockmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":models/block/" + name),
          new CustomModelBlock(BakedModelEditableSlant.class, defaultTex, defaultTex));
      CustomModelLoader.itemmodels.put(new ResourceLocation(getRegistryName().getResourceDomain() + ":" + name + "#inventory"),
          new CustomModelBlock(BakedModelEditableSlant.class, defaultTex, defaultTex));
    }
  }

  @Override
  public IUnlistedProperty<IBlockState> getStateProperty() {
    return STATEPROP;
  }

}
