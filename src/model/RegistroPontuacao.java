	package model;
	
	public class RegistroPontuacao {
		private Usuario usuario;
		private Jogo jogo;
		private int pontuacao;
	
		public RegistroPontuacao(Usuario usuario, Jogo jogo, int pontuacao) {
			this.usuario = usuario;
			this.jogo = jogo;
			this.pontuacao = pontuacao;
		}
	
		public Usuario getUsuario() {
			return usuario;
		}
	
		public Jogo getJogo() {
			return jogo;
		}
	
		public int getPontuacao() {
			return pontuacao;
		}
	        @Override
	        public String toString(){
	            return usuario.getNome() + "; " + jogo.getNome() + "; " + pontuacao + ";";
	        }
	}
