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
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.model.IModelState;

public class BakedModelEditableStairs extends BakedModelEditable {
  public static Map<String, List<BakedQuad>> data = new HashMap<>();
  Cube baked_cube_down;
  Cube baked_q_up_pz;

  public static Vec4f FULL_FACE_UV = new Vec4f(0, 0, 16, 16);
  public static Vec4f BOTTOM_SIDE_UV = new Vec4f(0, 8, 16, 8);
  public static Vec4f TOP_SIDE_UV = new Vec4f(0, 0, 16, 8);
  public static Vec4f[] bottomUV = new Vec4f[] { BOTTOM_SIDE_UV, BOTTOM_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, BOTTOM_SIDE_UV, BOTTOM_SIDE_UV };
  public static Vec4f[] topUV = new Vec4f[] { TOP_SIDE_UV, TOP_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, TOP_SIDE_UV, TOP_SIDE_UV };

  public BakedModelEditableStairs(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
      CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    baked_cube_down = ModelUtil.makeCube(format, 0, 0, 0, 1, 0.5, 1, null, texes, 0);
    baked_q_up_pz = ModelUtil.makeCube(format, 0, 0.5, 0.5, 1, 0.5, 0.5, null, texes, 0);
  }

  @Override
  public void addGeometry(List<BakedQuad> quads, EnumFacing side, IBlockState state, TextureAtlasSprite[] texes, int tintIndex) {
    EnumHalf half = state.getValue(BlockStairs.HALF);
    EnumShape shape = state.getValue(BlockStairs.SHAPE);
    EnumFacing face = state.getValue(BlockStairs.FACING);
    Cube cube_down, cube_up;
    Cube c_down_nxnz, c_down_pxnz, c_down_pxpz, c_down_nxpz;
    Cube q_down_nx, q_down_px, q_down_nz, q_down_pz;
    Cube q_up_nx, q_up_px, q_up_nz, q_up_pz;
    Cube e_down_nxnz, e_down_pxnz, e_down_pxpz, e_down_nxpz;
    Cube e_up_nxnz, e_up_pxnz, e_up_pxpz, e_up_nxpz;
    cube_down = ModelUtil.makeCube(format, 0, 0.5, 0, 1, 0.5, 1, null, texes, new Function<Vec3d, Vec3d>() {
      @Override
      public Vec3d apply(Vec3d arg0) {
        return arg0.add(0, -0.5, 0);
      }
    }, tintIndex);
    cube_up = ModelUtil.makeCube(format, 0, 0.5, 0, 1, 0.5, 1, null, texes, tintIndex);
    q_up_nx = ModelUtil.makeCube(format, 0, 0.5, 0, 0.5, 0.5, 1, null, texes, tintIndex);
    q_up_px = ModelUtil.makeCube(format, 0.5, 0.5, 0, 0.5, 0.5, 1, null, texes, tintIndex);
    q_up_nz = ModelUtil.makeCube(format, 0, 0.5, 0, 1, 0.5, 0.5, null, texes, tintIndex);
    q_up_pz = ModelUtil.makeCube(format, 0, 0.5, 0.5, 1, 0.5, 0.5, null, texes, tintIndex);
    q_down_nx = ModelUtil.makeCube(format, 0, 0, 0, 0.5, 0.5, 1, null, texes, tintIndex);
    q_down_px = ModelUtil.makeCube(format, 0.5, 0, 0, 0.5, 0.5, 1, null, texes, tintIndex);
    q_down_nz = ModelUtil.makeCube(format, 0, 0, 0, 1, 0.5, 0.5, null, texes, tintIndex);
    q_down_pz = ModelUtil.makeCube(format, 0, 0, 0.5, 1, 0.5, 0.5, null, texes, tintIndex);
    e_up_nxnz = ModelUtil.makeCube(format, 0, 0.5, 0, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_up_pxnz = ModelUtil.makeCube(format, 0.5, 0.5, 0, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_up_pxpz = ModelUtil.makeCube(format, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_up_nxpz = ModelUtil.makeCube(format, 0, 0.5, 0.5, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_down_nxnz = ModelUtil.makeCube(format, 0, 0, 0, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_down_pxnz = ModelUtil.makeCube(format, 0.5, 0, 0, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_down_pxpz = ModelUtil.makeCube(format, 0.5, 0, 0.5, 0.5, 0.5, 0.5, null, texes, tintIndex);
    e_down_nxpz = ModelUtil.makeCube(format, 0, 0, 0.5, 0.5, 0.5, 0.5, null, texes, tintIndex);
    Function<Vec3d, Vec3d> down = t -> t.add(0, -0.5, 0);
    c_down_nxnz = ModelUtil.makeCube(format, 0, 0.5, 0, 0.5, 0.5, 0.5, null, texes, down, tintIndex);
    c_down_pxnz = ModelUtil.makeCube(format, 0.5, 0.5, 0, 0.5, 0.5, 0.5, null, texes, down, tintIndex);
    c_down_pxpz = ModelUtil.makeCube(format, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, null, texes, down, tintIndex);
    c_down_nxpz = ModelUtil.makeCube(format, 0, 0.5, 0.5, 0.5, 0.5, 0.5, null, texes, down, tintIndex);
    if (half == EnumHalf.BOTTOM) {
      if (shape == EnumShape.STRAIGHT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          e_down_pxnz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          c_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          q_up_px.addToList(quads, side);
          break;
        case NORTH:
          e_down_nxnz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          q_up_nz.addToList(quads, side);
          break;
        case SOUTH:
          e_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          c_down_nxnz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          q_up_pz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          e_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          q_up_nx.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.OUTER_LEFT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          c_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          e_up_pxnz.addToList(quads, side);
          break;
        case NORTH:
          e_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          e_up_nxnz.addToList(quads, side);
          break;
        case SOUTH:
          c_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          e_up_pxpz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          c_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          e_up_nxpz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.OUTER_RIGHT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          c_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          e_up_pxpz.addToList(quads, side);
          break;
        case NORTH:
          c_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          e_up_pxnz.addToList(quads, side);
          break;
        case SOUTH:
          c_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          e_up_nxpz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          e_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          e_up_nxnz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.INNER_LEFT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          e_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          q_up_px.addToList(quads, side);
          e_up_nxnz.addToList(quads, side);
          break;
        case NORTH:
          e_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          q_up_nz.addToList(quads, side);
          e_up_nxpz.addToList(quads, side);
          break;
        case SOUTH:
          c_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          q_up_pz.addToList(quads, side);
          e_up_pxnz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          e_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          q_up_nx.addToList(quads, side);
          e_up_pxpz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.INNER_RIGHT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          c_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          q_up_px.addToList(quads, side);
          e_up_nxpz.addToList(quads, side);
          break;
        case NORTH:
          e_down_nxnz.addToList(quads, side);
          c_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          q_up_nz.addToList(quads, side);
          e_up_pxpz.addToList(quads, side);
          break;
        case SOUTH:
          e_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          c_down_pxnz.addToList(quads, side);
          q_up_pz.addToList(quads, side);
          e_up_nxnz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          e_down_nxnz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          c_down_pxpz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          q_up_nx.addToList(quads, side);
          e_up_pxnz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
    } else if (half == EnumHalf.TOP) {
      cube_up.addToList(quads, side);
      if (shape == EnumShape.STRAIGHT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          q_down_px.addToList(quads, side);
          break;
        case NORTH:
          q_down_nz.addToList(quads, side);
          break;
        case SOUTH:
          q_down_pz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          q_down_nx.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.OUTER_LEFT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          e_down_pxnz.addToList(quads, side);
          break;
        case NORTH:
          e_down_nxnz.addToList(quads, side);
          break;
        case SOUTH:
          e_down_pxpz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          e_down_nxpz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.OUTER_RIGHT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          e_down_pxpz.addToList(quads, side);
          break;
        case NORTH:
          e_down_pxnz.addToList(quads, side);
          break;
        case SOUTH:
          e_down_nxpz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          e_down_nxnz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.INNER_LEFT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          q_down_px.addToList(quads, side);
          e_down_nxnz.addToList(quads, side);
          break;
        case NORTH:
          q_down_nz.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          break;
        case SOUTH:
          q_down_pz.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          q_down_nx.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
      if (shape == EnumShape.INNER_RIGHT) {
        switch (face) {
        case DOWN:
          break;
        case EAST:
          q_down_px.addToList(quads, side);
          e_down_nxpz.addToList(quads, side);
          break;
        case NORTH:
          q_down_nz.addToList(quads, side);
          e_down_pxpz.addToList(quads, side);
          break;
        case SOUTH:
          q_down_pz.addToList(quads, side);
          e_down_nxnz.addToList(quads, side);
          break;
        case UP:
          break;
        case WEST:
          q_down_nx.addToList(quads, side);
          e_down_pxnz.addToList(quads, side);
          break;
        default:
          break;
        }
      }
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
    baked_cube_down.addToList(quads, side);
    baked_q_up_pz.addToList(quads, side);
  }

}
