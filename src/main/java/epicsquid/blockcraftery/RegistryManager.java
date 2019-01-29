package epicsquid.blockcraftery;

import epicsquid.blockcraftery.block.BlockEditableCorner;
import epicsquid.blockcraftery.block.BlockEditableCube;
import epicsquid.blockcraftery.block.BlockEditableFence;
import epicsquid.blockcraftery.block.BlockEditableSlab;
import epicsquid.blockcraftery.block.BlockEditableSlant;
import epicsquid.blockcraftery.block.BlockEditableStairs;
import epicsquid.blockcraftery.block.BlockEditableWall;
import epicsquid.blockcraftery.block.EditableBlockColors;
import epicsquid.blockcraftery.tile.TileEditableBlock;
import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.block.BlockSlabBase;
import epicsquid.mysticallib.event.RegisterColorHandlersEvent;
import epicsquid.mysticallib.event.RegisterContentEvent;
import epicsquid.mysticallib.event.RegisterModRecipesEvent;
import epicsquid.mysticallib.recipe.RecipeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RegistryManager {
  private static int entityId = 0;

  public static Block editable_block, editable_stairs, editable_slab, editable_double_slab, editable_slant, editable_outer_corner, editable_inner_corner, editable_wall, editable_fence, editable_block_reinforced, editable_stairs_reinforced, editable_slab_reinforced, editable_double_slab_reinforced, editable_slant_reinforced, editable_outer_corner_reinforced, editable_inner_corner_reinforced, editable_wall_reinforced, editable_fence_reinforced;

  @SubscribeEvent
  public void registerContent(RegisterContentEvent event) {
    LibRegistry.setActiveMod(Blockcraftery.MODID, Blockcraftery.CONTAINER);
    editable_block = event.addBlock(new BlockEditableCube(Material.WOOD, SoundType.WOOD, 1.0f, "editable_block", TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    editable_stairs = event.addBlock(new BlockEditableStairs(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_stairs", TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    BlockSlabBase editable_double_slab_temp = (BlockSlabBase) (new BlockEditableSlab(Material.WOOD, SoundType.WOOD, 1.0f, "editable" + "_double_slab", editable_block.getDefaultState(), true, null, TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    BlockSlabBase editable_slab_temp = (BlockSlabBase) (new BlockEditableSlab(Material.WOOD, SoundType.WOOD, 1.0f, "editable" + "_slab", editable_block.getDefaultState(), false, editable_double_slab_temp, TileEditableBlock.class).setResistance(0f).setFlammable(true)
        .setCreativeTab(Blockcraftery.tab));
    editable_double_slab_temp.slab = editable_slab_temp;
    editable_slab = event.addBlock(editable_slab_temp.setCreativeTab(Blockcraftery.tab));
    editable_double_slab = event.addBlock(editable_double_slab_temp.setCreativeTab(Blockcraftery.tab));
    editable_slant = event.addBlock(new BlockEditableSlant(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_slant", TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    editable_outer_corner = event.addBlock(new BlockEditableCorner(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_outer_corner", false, TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    editable_inner_corner = event.addBlock(new BlockEditableCorner(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_inner_corner", true, TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    editable_wall = event.addBlock(new BlockEditableWall(editable_block, SoundType.WOOD, 1.0f, "editable_wall", TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));
    editable_fence = event.addBlock(new BlockEditableFence(editable_block, SoundType.WOOD, 1.0f, "editable_fence", TileEditableBlock.class).setResistance(0f).setFlammable(true).setCreativeTab(Blockcraftery.tab));

    editable_block_reinforced = event.addBlock(new BlockEditableCube(Material.WOOD, SoundType.WOOD, 1.0f, "editable_block_reinforced", TileEditableBlock.class).setResistance(6000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
    editable_stairs_reinforced = event.addBlock(new BlockEditableStairs(editable_block_reinforced.getDefaultState(), SoundType.WOOD, 1.0f, "editable_stairs_reinforced", TileEditableBlock.class).setResistance(6000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
    BlockSlabBase editable_double_slab_reinforced_temp = (BlockSlabBase) (new BlockEditableSlab(Material.WOOD, SoundType.WOOD, 1.0f, "editable" + "_double_slab" + "_reinforced", editable_block_reinforced.getDefaultState(), true, null, TileEditableBlock.class).setResistance(6000f).setFlammable(false)
        .setCreativeTab(Blockcraftery.tab));
    BlockSlabBase editable_slab_reinforced_temp = (BlockSlabBase) (new BlockEditableSlab(Material.WOOD, SoundType.WOOD, 1.0f, "editable" + "_slab" + "_reinforced", editable_block_reinforced.getDefaultState(), false, editable_double_slab_temp, TileEditableBlock.class).setResistance(6000f)
        .setFlammable(false).setCreativeTab(Blockcraftery.tab));
    editable_double_slab_reinforced_temp.slab = editable_slab_reinforced_temp;
    editable_slab_reinforced = event.addBlock(editable_slab_temp.setCreativeTab(Blockcraftery.tab));
    editable_double_slab_reinforced = event.addBlock(editable_double_slab_temp.setCreativeTab(Blockcraftery.tab));
    editable_slant_reinforced = event.addBlock(new BlockEditableSlant(editable_block_reinforced.getDefaultState(), SoundType.WOOD, 1.0f, "editable_slant_reinforced", TileEditableBlock.class).setResistance(6000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
    editable_outer_corner_reinforced = event.addBlock(new BlockEditableCorner(editable_block_reinforced.getDefaultState(), SoundType.WOOD, 1.0f, "editable_outer_corner_reinforced", false, TileEditableBlock.class).setResistance(60000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
    editable_inner_corner_reinforced = event.addBlock(new BlockEditableCorner(editable_block_reinforced.getDefaultState(), SoundType.WOOD, 1.0f, "editable_inner_corner_reinforced", true, TileEditableBlock.class).setResistance(60000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
    editable_wall_reinforced = event.addBlock(new BlockEditableWall(editable_block_reinforced, SoundType.WOOD, 1.0f, "editable_wall_reinforced", TileEditableBlock.class).setResistance(60000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
    editable_fence_reinforced = event.addBlock(new BlockEditableFence(editable_block_reinforced, SoundType.WOOD, 1.0f, "editable_fence_reinforced", TileEditableBlock.class).setResistance(60000f).setFlammable(false).setCreativeTab(Blockcraftery.tab));
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerColorHandlers(RegisterColorHandlersEvent event) {
    Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new EditableBlockColors(), editable_block, editable_stairs, editable_slab, editable_double_slab, editable_slant, editable_outer_corner, editable_inner_corner, editable_wall, editable_wall, editable_fence);
  }

  @SubscribeEvent
  public void onRegisterRecipes(RegisterModRecipesEvent event) {
    LibRegistry.setActiveMod(Blockcraftery.MODID, Blockcraftery.CONTAINER);

    RecipeRegistry.registerShaped(event.getRegistry(), "editable_block", new ItemStack(editable_block, 2), "S S", "   ", "S S", 'S', "stickWood");
    RecipeRegistry.registerShaped(event.getRegistry(), "editable_slab", new ItemStack(editable_slab, 3), "SSS", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_stairs", new ItemStack(editable_stairs, 4), "  S", " SS", "SSS", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_wall", new ItemStack(editable_wall, 6), "SSS", "SSS", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_slant", new ItemStack(editable_slant, 4), " S", "S ", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_inner_corner", new ItemStack(editable_inner_corner, 2), " S", "SS", 'S', editable_slant);
    RecipeRegistry.registerShapeless(event.getRegistry(), "editable_outer_corner", new ItemStack(editable_outer_corner, 2), editable_slant);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_fence", new ItemStack(editable_fence, 6), "SWS", "SWS", 'S', editable_block, 'W', "stickWood");

    RecipeRegistry.registerShaped(event.getRegistry(), "editable_block_reinforced", new ItemStack(editable_block_reinforced, 2), "SWS", " W ", "SWS", 'S', "stickWood", 'W', "nuggetIron");
    RecipeRegistry.registerShaped(event.getRegistry(), "editable_block_reinforced_upgrade", new ItemStack(editable_block_reinforced, 1), " W ", "WSW", " S ", 'S', editable_block, 'W', "nuggetIron");
    RecipeRegistry.registerShaped(event.getRegistry(), "editable_slab_reinforced", new ItemStack(editable_slab_reinforced, 3), "SSS", 'S', editable_block_reinforced);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_stairs_reinforced", new ItemStack(editable_stairs_reinforced, 4), "  S", " SS", "SSS", 'S', editable_block_reinforced);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_wall_reinforced", new ItemStack(editable_wall_reinforced, 6), "SSS", "SSS", 'S', editable_block_reinforced);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_slant_reinforced", new ItemStack(editable_slant_reinforced, 4), " S", "S ", 'S', editable_block_reinforced);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_inner_corner_reinforced", new ItemStack(editable_inner_corner_reinforced, 2), " S", "SS", 'S', editable_slant_reinforced);
    RecipeRegistry.registerShapeless(event.getRegistry(), "editable_outer_corner_reinforced", new ItemStack(editable_outer_corner_reinforced, 2), editable_slant_reinforced);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_fence_reinforced", new ItemStack(editable_fence_reinforced, 6), "SWS", "SWS", 'S', editable_block_reinforced, 'W', "stickWood");
  }
}
