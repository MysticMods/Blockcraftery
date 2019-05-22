package epicsquid.blockcraftery.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import epicsquid.mysticallib.model.CustomModelBase;
import epicsquid.mysticallib.model.DefaultTransformations;
import epicsquid.mysticallib.model.ModelUtil;
import epicsquid.mysticallib.model.parts.Cube;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class BakedModelEditablePressurePlate extends BakedModelEditable {
  public static Map<String, List<BakedQuad>> data = new HashMap<>();

  private Cube cube_off;

  public BakedModelEditablePressurePlate(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
      CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    cube_off = ModelUtil.makeCube(format, 0.0625, 0, 0.0625, 0.875, 0.0625, 0.875, null, texes, 0).setNoCull(EnumFacing.DOWN);
  }

  @Override
  public void addGeometry(List<BakedQuad> quads, EnumFacing side, IBlockState state, TextureAtlasSprite[] texes, int tintIndex) {
    Cube cube_on, cube_off;
    cube_on = ModelUtil.makeCube(format, 0.0625, 0, 0.0625, 0.875, 0.03125, 0.875, null, texes, 0).setNoCull(EnumFacing.DOWN);
    cube_off = ModelUtil.makeCube(format, 0.0625, 0, 0.0625, 0.875, 0.0625, 0.875, null, texes, 0).setNoCull(EnumFacing.DOWN);

    boolean on = state.getValue(BlockPressurePlate.POWERED);
    if (on) {
      cube_on.addToList(quads, side);
    } else {
      cube_off.addToList(quads, side);
    }
  }

  @Override
  public boolean isAmbientOcclusion() {
    return true;
  }

  @Override
  public boolean isGui3d() {
    return true;
  }

  @Override
  public boolean isBuiltInRenderer() {
    return false;
  }

  @Override
  public TextureAtlasSprite getParticleTexture() {
    return particle;
  }

  @Override
  public ItemOverrideList getOverrides() {
    return new ItemOverrideList(Arrays.asList());
  }

  @Override
  public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
    Matrix4f matrix = null;
    if (DefaultTransformations.blockTransforms.containsKey(type)) {
      matrix = DefaultTransformations.blockTransforms.get(type).getMatrix();
      return Pair.of(this, matrix);
    }
    return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, type);
  }

  @Override
  public void addItemModel(List<BakedQuad> quads, EnumFacing side) {
    cube_off.addToList(quads, side);
  }

}
