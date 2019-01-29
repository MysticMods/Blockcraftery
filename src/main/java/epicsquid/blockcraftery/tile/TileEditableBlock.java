package epicsquid.blockcraftery.tile;

import javax.annotation.Nonnull;

import epicsquid.blockcraftery.ConfigManager;
import epicsquid.blockcraftery.block.BlockEditableCube;
import epicsquid.blockcraftery.block.IEditableBlock;
import epicsquid.mysticallib.tile.TileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEditableBlock extends TileBase {
  public IBlockState state = Blocks.AIR.getDefaultState();
  public ItemStack stack = ItemStack.EMPTY;
  private boolean hasGlowstone = false;

  @Override
  public boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
    if (hand == EnumHand.OFF_HAND) {
      return false;
    }
    if ((this.state.getBlock() == Blocks.AIR || ConfigManager.rightClickReplace) && !player.getHeldItemMainhand().isEmpty() && Block.getBlockFromItem(player.getHeldItemMainhand().getItem()) != Blocks.AIR) {
      Block b = Block.getBlockFromItem(player.getHeldItemMainhand().getItem());
      if (b != null && !(b instanceof IEditableBlock) && b != Blocks.AIR) {
        IBlockState newState = b.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, player.getHeldItemMainhand().getMetadata(), player);
        if (newState != this.state && newState.isFullCube() || b instanceof BlockBreakable) {
          this.state = newState;
          markDirty();
          if (!player.capabilities.isCreativeMode && !ConfigManager.freeDecoration) {
            if (!world.isRemote && !player.capabilities.isCreativeMode && !stack.isEmpty()) {
              world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
            }
            this.stack = player.getHeldItemMainhand().copy();
            stack.setCount(1);
            player.getHeldItemMainhand().shrink(1);
            if (player.getHeldItemMainhand().isEmpty()) {
              player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
            }
          }
          if (ConfigManager.freeDecoration) {
            this.stack = ItemStack.EMPTY;
          }
          if (state.getBlock() instanceof BlockEditableCube) {
            world.setBlockState(pos, state.withProperty(BlockEditableCube.OPAQUECUBE, newState.isOpaqueCube()).withProperty(BlockEditableCube.FULLCUBE, !newState.isFullCube()));
          }
          world.notifyBlockUpdate(pos, state, newState, 8);
          return true;
        }
      }
    } else if (player.getHeldItemMainhand().getItem() == Items.GLOWSTONE_DUST) {
      world.setBlockState(pos, state.withProperty(BlockEditableCube.LIGHT, true));
      if (!player.capabilities.isCreativeMode) {
        player.getHeldItemMainhand().shrink(1);
        if (player.getHeldItemMainhand().isEmpty()) {
          player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        }
      }
      hasGlowstone = true;
    } else if (player.isSneaking() && player.getHeldItemMainhand().isEmpty()) {
      if (!world.isRemote && !player.capabilities.isCreativeMode && !ConfigManager.freeDecoration) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
      }
      stack = ItemStack.EMPTY;
      this.state = Blocks.AIR.getDefaultState();
      markDirty();
      if (state.getBlock() instanceof BlockEditableCube) {
        world.setBlockState(pos, state.withProperty(BlockEditableCube.OPAQUECUBE, this.state.isOpaqueCube()).withProperty(BlockEditableCube.FULLCUBE, !this.state.isFullCube()));
      }
      world.notifyBlockUpdate(pos, state, state, 8);
      return true;
    }
    return false;
  }

  @Override
  public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player) {
    super.breakBlock(world, pos, state, player);
    if (!world.isRemote && !player.capabilities.isCreativeMode) {
      world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
      if (hasGlowstone) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Items.GLOWSTONE_DUST)));
      }
    }
  }

  @Override
  public boolean shouldRefresh(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState oldState, @Nonnull IBlockState newState) {
    return oldState.getBlock() != newState.getBlock();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    state = NBTUtil.readBlockState(tag.getCompoundTag("state"));
    stack = new ItemStack(tag.getCompoundTag("stack"));
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    NBTTagCompound tag = super.writeToNBT(nbt);
    tag.setTag("state", NBTUtil.writeBlockState(new NBTTagCompound(), state));
    tag.setTag("stack", stack.writeToNBT(new NBTTagCompound()));
    return tag;
  }
}
