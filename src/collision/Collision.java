package collision;

public class Collision
{
    String[] tetromino;
    int nFieldHeight = 18;
    int nFieldWidth = 12;
    char pField;

    public int Rotate(int px, int py, int r)
    {
        int pi = 0;
        return pi;
    }
    public boolean DoesPieceFit(int nTetromino, int nRotation, int nPosX, int nPosY)
    {
        for(int px = 0; px < 4; px++)
        {
            for(int py = 0; py < 4; py++)
            {
                int pi = Rotate(px,, py, nRotation);
                int fi = (nPosY+py) * nFieldWidth + (nPosX + px);

                if (nPosX + px >= 0 && nPosX + px < nFieldWidth)
                {
                    if (nPosY + py >= 0 && nPosY + py < nFieldHeight)
                    {
                        if(tetromino[nTetromino][pi] != '.' && pField[fi] != 0)
                            return false;
                    }
                }
            }
        }
        return true;
    }
}
