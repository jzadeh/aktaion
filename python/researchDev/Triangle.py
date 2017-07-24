# x = x xor y
# y = x xor y
# x = x xor y

def Triangle(base, height):
    """Return the area of Triangle"""
    area = 0.5 * base * height
    return area


base = input("Enter triangle base length: ")
height = input("Enter triangle height: ")

base = float(base)
height = float(height)

print('Area of triangle is', Triangle(base, height))

area = Triangle(base, height)
print('The area of the triangle is', area)

