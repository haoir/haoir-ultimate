package haoirdevelopment.haoirclient.utils.render.postprocess;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import haoirdevelopment.haoirclient.mixininterface.ILevelRenderer;
import net.minecraft.world.entity.Entity;

import static haoirdevelopment.haoirclient.HaOirClient.mc;

public abstract class EntityShader extends PostProcessShader {
    protected EntityShader(RenderPipeline pipeline) {
        super(pipeline);
    }

    public abstract boolean shouldDraw(Entity entity);

    @Override
    protected void preDraw() {
        ((ILevelRenderer) mc.levelRenderer).meteor$pushEntityOutlineFramebuffer(framebuffer);
    }

    @Override
    protected void postDraw() {
        ((ILevelRenderer) mc.levelRenderer).meteor$popEntityOutlineFramebuffer();
    }

    public void submitVertices() {
    }
}
