CREATE TABLE IF NOT EXISTS `algamoney-api`.`categorias` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `algamoney-api`.`categorias` (`id`, `nome`) VALUES (1, 'Lazer');
INSERT INTO `algamoney-api`.`categorias` (`id`, `nome`) VALUES (2, 'Alimentação');
INSERT INTO `algamoney-api`.`categorias` (`id`, `nome`) VALUES (3, 'Supermercado');
INSERT INTO `algamoney-api`.`categorias` (`id`, `nome`) VALUES (4, 'Farmácia');
INSERT INTO `algamoney-api`.`categorias` (`id`, `nome`) VALUES (5, 'Outros');
