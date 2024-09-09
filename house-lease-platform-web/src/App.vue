<template>
  <canvas id="particleCanvas"></canvas>
  <router-view></router-view>
</template>

<script setup>
import { onMounted } from 'vue'

let rendering = true; // 新增标志位

onMounted(() => {
  initParticleCanvas();
});

function initParticleCanvas() {
  const canvas = document.getElementById('particleCanvas');
  const ctx = canvas.getContext('2d');
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight;

  const particles = [];
  const particleCount = 50;

  // 粒子类
  class Particle {
    constructor() {
      this.x = Math.random() * canvas.width;
      this.y = Math.random() * canvas.height;
      this.vx = (Math.random() - 0.5) * 0.7;
      this.vy = (Math.random() - 0.5) * 0.7;
      this.size = Math.random() * 2 + 1;
    }

    update() {
      this.x += this.vx;
      this.y += this.vy;

      // 边界反弹
      if (this.x > canvas.width || this.x < 0) this.vx *= -1;
      if (this.y > canvas.height || this.y < 0) this.vy *= -1;

      this.draw();
    }

    draw() {
      ctx.fillStyle = 'rgba(255, 255, 255, 0.8)';
      ctx.beginPath();
      ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
      ctx.closePath();
      ctx.fill();
    }
  }

  // 生成粒子
  for (let i = 0; i < particleCount; i++) {
    particles.push(new Particle());
  }

  // 更新粒子和连线
  function updateParticles() {
    if (!rendering) return; // 根据标志位决定是否渲染

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    for (let i = 0; i < particles.length; i++) {
      const p1 = particles[i];
      p1.update();

      for (let j = i + 1; j < particles.length; j++) {
        const p2 = particles[j];
        const dist = Math.sqrt((p1.x - p2.x) ** 2 + (p1.y - p2.y) ** 2);

        if (dist < 200) {
          ctx.strokeStyle = `rgba(255, 255, 255, ${1 - dist / 200})`;
          ctx.lineWidth = 0.5;
          ctx.beginPath();
          ctx.moveTo(p1.x, p1.y);
          ctx.lineTo(p2.x, p2.y);
          ctx.stroke();
        }
      }
    }

    requestAnimationFrame(updateParticles);
  }

  updateParticles();

  // 关闭粒子渲染
  function stopRendering() {
    rendering = false;
  }

  //暴露 stopRendering 方法到 Vue 实例或全局对象
  window.stopParticleRendering = stopRendering;
}

</script>

<style scoped>
#particleCanvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  background-color: #54a1ea;
}
</style>
