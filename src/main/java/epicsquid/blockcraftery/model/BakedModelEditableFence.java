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

public class BakedModelEditableFence extends BakedModelEditable {
  public static Map<String, List<BakedQuad>> data = new HashMap<>();
  Cube baked_post_right, baked_post_left, baked_west, baked_west_top;

  public BakedModelEditableFence(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
      CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    baked_post_right = makePostCube(format, 0, 0, 0.375, 0.25, 1, 0.25, null, texes, 0);
    baked_post_left = makePostCube(format, 0.75, 0, 0.375, 0.25, 1, 0.25, null, texes, 0);
    baked_west = ModelUtil.makeCube(format, 0, 0.375, 0.4375, 1, 0.1875, 0.125, null, texes, 0);
    baked_west_top = ModelUtil.makeCube(format, 0, 0.75, 0.4375, 1, 0.1875, 0.125, null, texes, 0);
  }

  @Override
  public void addGeometry(List<BakedQuad> quads, EnumFacing side, IBlockState state, TextureAtlasSprite[] texes, int tintIndex) {
    Cube post, north, south, west, east, north_top, south_top, west_top, east_top;
    boolean cnorth = state.getValue(BlockWall.NORTH);
    boolean csouth = state.getValue(BlockWall.SOUTH);
    boolean cwest = state.getValue(BlockWall.WEST);
    boolean ceast = state.getValue(BlockWall.EAST);
    post = makePostCube(format, 0.375, 0, 0.375, 0.25, 1, 0.25, null, texes, tintIndex);
    north = ModelUtil.makeCube(format, 0.4375, 0.375, 0, 0.125, 0.1875, 0.5, null, texes, tintIndex);
    south = ModelUtil.makeCube(format, 0.4375, 0.375, 0.5, 0.125, 0.1875, 0.5, null, texes, tintIndex);
    west = ModelUtil.makeCube(format, 0, 0.375, 0.4375, 0.5, 0.1875, 0.125, null, texes, tintIndex);
    east = ModelUtil.makeCube(format, 0.5, 0.375, 0.4375, 0.5, 0.1875, 0.125, null, texes, tintIndex);

    north_top = ModelUtil.makeCube(format, 0.4375, 0.75, 0, 0.125, 0.1875, 0.5, null, texes, tintIndex);
    south_top = ModelUtil.makeCube(format, 0.4375, 0.75, 0.5, 0.125, 0.1875, 0.5, null, texes, tintIndex);
    west_top = ModelUtil.makeCube(format, 0, 0.75, 0.4375, 0.5, 0.1875, 0.125, null, texes, tintIndex);
    east_top = ModelUtil.makeCube(format, 0.5, 0.75, 0.4375, 0.5, 0.1875, 0.125, null, texes, tintIndex);
    post.addToList(quads, side);
    if (cnorth) {
      north.addToList(quads, side);
      north_top.addToList(quads, side);
    }
    if (csouth) {
      south.addToList(quads, side);
      south_top.addToList(quads, side);
    }
    if (cwest) {
      west.addToList(quads, side);
      west_top.addToList(quads, side);
    }
    if (ceast) {
      east.addToList(quads, side);
      east_top.addToList(quads, side);
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
    baked_post_right.addToList(quads, side);
    baked_post_left.addToList(quads, side);
    baked_west.addToList(quads, side);
    baked_west_top.addToList(quads, side);
  }

  private Cube makePostCube(VertexFormat format, double x, double y, double z, double w, double h, double l, Vec4f[] uv, TextureAtlasSprite[] sprites, int tintIndex) {
    uv = new Vec4f[]{new Vec4f((float)z * 16.0F, (float)(-y) * 16.0F + (16.0F - (float)h * 16.0F), (float)l * 16.0F, (float)h * 16.0F), new Vec4f(16.0F - (float)l * 16.0F - (float)z * 16.0F, (float)(-y) * 16.0F + (16.0F - (float)h * 16.0F), (float)l * 16.0F, (float)h * 16.0F), new Vec4f(5f, 5f, 6f, 6f), new Vec4f(5f, 5f, 6f, 6f), new Vec4f(16.0F - (float)w * 16.0F - (float)x * 16.0F, (float)(-y) * 16.0F + (16.0F - (float)h * 16.0F), (float)w * 16.0F, (float)h * 16.0F), new Vec4f((float)x * 16.0F, (float)(-y) * 16.0F + (16.0F - (float)h * 16.0F), (float)w * 16.0F, (float)h * 16.0F)};
    return ModelUtil.makeCube(format, x, y, z, w, h, l, uv, sprites, tintIndex);
  }

}