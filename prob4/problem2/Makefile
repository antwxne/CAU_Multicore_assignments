# Var
SRC	=	./omp_pi_one.c

OBJ	=	$(SRC:.c=.o)
NAME	=	omp_pi_one
CC	=	gcc
RM	?= rm -f

# Flags

CFLAGS	=	-Wall -Wextra -fopenmp
CPPFLAGS	=
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