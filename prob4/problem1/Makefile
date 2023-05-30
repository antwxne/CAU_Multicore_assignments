# Var
SRC	=	./openmp_ray.cpp

OBJ	=	$(SRC:.cpp=.o)
NAME	=	openmp_ray
CC	=	g++
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