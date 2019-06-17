package entity;

import java.util.Date;

public class Pet
{
  private long id;
  private String name;
  private Date birthDate;
  private String type;

  public Pet(String name, Date birthDate, String type)
  {
    this(0, name, birthDate, type);
  }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", type='" + type + '\'' +
                '}' +
                "\n";
    }

    public Pet(long id, String name, Date birthDate, String type)
  {
    this.id = id;
    this.name = name;
    this.birthDate = birthDate;
    this.type = type;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Date getBirthDate()
  {
    return birthDate;
  }

  public void setBirthDate(Date birthDate)
  {
    this.birthDate = birthDate;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Pet pet = (Pet) o;

    if (id != pet.id)
    {
      return false;
    }
    if (!name.equals(pet.name))
    {
      return false;
    }
    if (!birthDate.equals(pet.birthDate))
    {
      return false;
    }
    return type.equals(pet.type);
  }

  @Override
  public int hashCode()
  {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + name.hashCode();
    result = 31 * result + birthDate.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }
}
