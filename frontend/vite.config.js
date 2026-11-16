
import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// Configuración del servidor de desarrollo y comunicación con Java.
export default defineConfig({
  plugins: [react()],

  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080/web-electricos',
        changeOrigin: true,
      },
    },
  },
})
