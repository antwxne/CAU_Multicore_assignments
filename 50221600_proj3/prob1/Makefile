# Var
SRC	=	./prob1.c	\
		./prime_counter.c


OBJ	=	$(SRC:.c=.o)
NAME	=	a.out
CC	=	gcc
RM	?= rm -f

# Flags

CFLAGS	=	-Wall -Wextra -fopenmp
CPPFLAGS	=	-iquote ./
LDFLAGS	=
LDLIBS	=	-fopenmp

# Rules
.PHONY:	all
all:	$(OBJ)
	$(CC) -o $(NAME) $(OBJ) $(LDFLAGS) $(LDLIBS)

.PHONY:	clean
clean:
	$(RM) $(OBJ)

.PHONY:	fclean
fclean:	clean
	$(RM) $(NAME)

.PHONY:	re
re: fclean all

.PHONY:	debug
debug: CFLAGS += -g3
debug: re