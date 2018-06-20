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
import epicsquid.mysticallib.struct.Vec4f;
import net.minecraft.block.BlockWall;
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

public class BakedModelEditableWall extends BakedModelEditable {
  public static Map<String, List<BakedQuad>> data = new HashMap<>();
  Cube baked_post, baked_west, baked_east;

  public static Vec4f FULL_FACE_UV = new Vec4f(0, 0, 16, 16);
  public static Vec4f BOTTOM_SIDE_UV = new Vec4f(0, 8, 16, 8);
  public static Vec4f TOP_SIDE_UV = new Vec4f(0, 0, 16, 8);
  public static Vec4f[] bottomUV = new Vec4f[] { BOTTOM_SIDE_UV, BOTTOM_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, BOTTOM_SIDE_UV, BOTTOM_SIDE_UV };
  public static Vec4f[] topUV = new Vec4f[] { TOP_SIDE_UV, TOP_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, TOP_SIDE_UV, TOP_SIDE_UV };

  public BakedModelEditableWall(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
      CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    baked_post = ModelUtil.makeCube(format, 0.25, 0, 0.25, 0.5, 1, 0.5, null, texes, -1);
    baked_west = ModelUtil.makeCube(format, 0, 0, 0.3125, 0.5, 0.875, 0.375, null, texes, -1);
    baked_east = ModelUtil.makeCube(format, 0.5, 0, 0.3125, 0.5, 0.875, 0.375, null, texes, -1);
  }

  @Override
  public void addGeometry(List<BakedQuad> quads, EnumFacing side, IBlockState state, TextureAtlasSprite[] texes, int tintIndex) {
    Cube post, north, south, west, east;
    boolean up = state.getValue(BlockWall.UP);
    boolean cnorth = state.getValue(BlockWall.NORTH);
    boolean csouth = state.getValue(BlockWall.SOUTH);
    boolean cwest = state.getValue(BlockWall.WEST);
    boolean ceast = state.getValue(BlockWall.EAST);
    post = ModelUtil.makeCube(format, 0.25, 0, 0.25, 0.5, 1, 0.5, null, texes, tintIndex);
    north = ModelUtil.makeCube(format, 0.3125, 0, 0, 0.375, 0.875, 0.5, null, texes, tintIndex);
    south = ModelUtil.makeCube(format, 0.3125, 0, 0.5, 0.375, 0.875, 0.5, null, texes, tintIndex);
    west = ModelUtil.makeCube(format, 0, 0, 0.3125, 0.5, 0.875, 0.375, null, texes, tintIndex);
    east = ModelUtil.makeCube(format, 0.5, 0, 0.3125, 0.5, 0.875, 0.375, null, texes, tintIndex);
    if (up) {
      post.addToList(quads, side);
    }
    if (cnorth) {
      north.addToList(quads, side);
    }
    if (csouth) {
      south.addToList(quads, side);
    }
    if (cwest) {
      west.addToList(quads, side);
    }
    if (ceast) {
      east.addToList(quads, side);
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
  public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
    Matrix4f matrix = null;
    if (DefaultTransformations.blockTransforms.containsKey(type)) {
      matrix = DefaultTransformations.blockTransforms.get(type).getMatrix();
      return Pair.of(this, matrix);
    }
    return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, type);
  }

  @Override
  public void addItemModel(List<BakedQuad> quads, EnumFacing side) {
    baked_post.addToList(quads, side);
    baked_east.addToList(quads, side);
    baked_west.addToList(quads, side);
  }

}
