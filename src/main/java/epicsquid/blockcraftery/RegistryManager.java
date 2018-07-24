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

  public static Block editable_block, editable_stairs, editable_slab, editable_double_slab, editable_slant, editable_outer_corner, editable_inner_corner, editable_wall, editable_fence;

  @SubscribeEvent
  public void registerContent(RegisterContentEvent event) {
    LibRegistry.setActiveMod(Blockcraftery.MODID, Blockcraftery.CONTAINER);
    editable_block = event
        .addBlock(new BlockEditableCube(Material.WOOD, SoundType.WOOD, 1.0f, "editable_block", TileEditableBlock.class).setCreativeTab(Blockcraftery.tab));
    editable_stairs = event.addBlock(new BlockEditableStairs(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_stairs", TileEditableBlock.class)
        .setCreativeTab(Blockcraftery.tab));
    BlockSlabBase editable_double_slab_temp = (BlockSlabBase) (new BlockEditableSlab(Material.WOOD, SoundType.WOOD, 1.0f, "editable" + "_double_slab",
        editable_block.getDefaultState(), true, null, TileEditableBlock.class).setCreativeTab(Blockcraftery.tab));
    BlockSlabBase editable_slab_temp = (BlockSlabBase) (new BlockEditableSlab(Material.WOOD, SoundType.WOOD, 1.0f, "editable" + "_slab",
        editable_block.getDefaultState(), false, editable_double_slab_temp, TileEditableBlock.class).setCreativeTab(Blockcraftery.tab));
    editable_double_slab_temp.slab = editable_slab_temp;
    editable_slab = event.addBlock(editable_slab_temp.setCreativeTab(Blockcraftery.tab));
    editable_double_slab = event.addBlock(editable_double_slab_temp.setCreativeTab(Blockcraftery.tab));
    editable_slant = event.addBlock(new BlockEditableSlant(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_slant", TileEditableBlock.class)
        .setCreativeTab(Blockcraftery.tab));
    editable_outer_corner = event.addBlock(
        new BlockEditableCorner(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_outer_corner", false, TileEditableBlock.class)
            .setCreativeTab(Blockcraftery.tab));
    editable_inner_corner = event.addBlock(
        new BlockEditableCorner(editable_block.getDefaultState(), SoundType.WOOD, 1.0f, "editable_inner_corner", true, TileEditableBlock.class)
            .setCreativeTab(Blockcraftery.tab));
    editable_wall = event
        .addBlock(new BlockEditableWall(editable_block, SoundType.WOOD, 1.0f, "editable_wall", TileEditableBlock.class).setCreativeTab(Blockcraftery.tab));
    editable_fence = event
        .addBlock(new BlockEditableFence(editable_block, SoundType.WOOD, 1.0f, "editable_fence", TileEditableBlock.class).setCreativeTab(Blockcraftery.tab));
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerColorHandlers(RegisterColorHandlersEvent event) {
    Minecraft.getMinecraft().getBlockColors()
        .registerBlockColorHandler(new EditableBlockColors(), editable_block, editable_stairs, editable_slab, editable_double_slab, editable_slant,
            editable_outer_corner, editable_inner_corner, editable_wall);
  }

  @SubscribeEvent
  public void onRegisterRecipes(RegisterModRecipesEvent event) {
    LibRegistry.setActiveMod(Blockcraftery.MODID, Blockcraftery.CONTAINER);

    RecipeRegistry.registerShaped(event.getRegistry(), "editable_block", new ItemStack(editable_block, 2), "S S", "   ", "S S", 'S', "stickWood");
    RecipeRegistry.registerShaped(event.getRegistry(), "editable_slab", new ItemStack(editable_slab, 3), "SSS", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_stairs", new ItemStack(editable_stairs, 4), "  S", " SS", "SSS", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_wall", new ItemStack(editable_wall, 6), "SSS", "SSS", 'S', editable_block);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_slant", new ItemStack(editable_slant, 4), " S", "S ", 'S', editable_block);
    RecipeRegistry
        .registerShapedMirrored(event.getRegistry(), "editable_inner_corner", new ItemStack(editable_inner_corner, 2), " S", "SS", 'S', editable_slant);
    RecipeRegistry.registerShapeless(event.getRegistry(), "editable_outer_corner", new ItemStack(editable_outer_corner, 2), editable_slant);
    RecipeRegistry.registerShapedMirrored(event.getRegistry(), "editable_fence", new ItemStack(editable_fence, 6), "SWS", "SWS", 'S', editable_block, 'W', "stickWood");
  }
}
