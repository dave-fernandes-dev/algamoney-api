CREATE TABLE IF NOT EXISTS categoria (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO categoria (`id`, `nome`) VALUES (1, 'Lazer');
INSERT INTO categoria (`id`, `nome`) VALUES (2, 'Alimentação');
INSERT INTO categoria (`id`, `nome`) VALUES (3, 'Supermercado');
INSERT INTO categoria (`id`, `nome`) VALUES (4, 'Farmácia');
INSERT INTO categoria (`id`, `nome`) VALUES (5, 'Outros');
