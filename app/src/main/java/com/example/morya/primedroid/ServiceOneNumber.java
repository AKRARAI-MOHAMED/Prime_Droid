package com.example.morya.primedroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by Morya on 09/04/2017.
 */

public class ServiceOneNumber extends Service {

    private final IBinder myBinder = new MyIBinder();

    public class MyIBinder extends Binder {
        ServiceOneNumber getService() {
            return ServiceOneNumber.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    boolean isPrime(int n) {
        //checks whether an int is prime or not.
        //check if n is a multiple of 2
        if ((n % 2 == 0 & n != 2) || n == 1) return false;
        if (n == 2) return true;

        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    int[] searchdeviders(int n) {

        int[] tab = new int[21];

        int[] tabto = new int[20];

        int j = 0;
        // Print the number of 2s that divide n
        while (n % 2 == 0) {
            tab[j] = 2;
            j++;
            n /= 2;
        }

        // n must be odd at this point.  So we can
        // skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            // While i divides n, print i and divide n
            while (n % i == 0) {
                tab[j] = i;
                j++;
                n /= i;
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2) {
            tab[j] = n;

        }
        int k = 0, s = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] != 0) {

                if (tab[i] == tab[i + 1]) {
                    k++;
                } else {
                    tabto[s] = tab[i];
                    tabto[s + 1] = k + 1;
                    k = 0;
                    s = s + 2;
                }

            }
        }
        return tabto;
    }

    String[] factorsDecomposition(int[] tab) {
        String[] poweredFactors = {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-"};
        int i;
        int j = 0;
        for (i = 0; i < tab.length; i = i + 2) {
            if (tab[i] != 0) {
                poweredFactors[j] = Integer.toString(tab[i]) + "^" + tab[i + 1];
                j++;
            }
        }
        return poweredFactors;
    }

    boolean compareTwonumbers(int numR1, int numR2) {
        int t;
        if (numR1 <= numR2) {
            int switchnum;
            switchnum = numR2;
            numR2 = numR1;
            numR1 = switchnum;
        }

        while (numR2 != 0) {
            t = numR2;

            numR2 = numR1 % t;

            numR1 = t;
        }
        return numR1 == 1;

    }

    //////////////////////
    String[] PrimeNumbersInrange(int minN, int maxN) {
        int count = 0;
        String[] tabPrimesInRange = new String[50000000];
        for (int i = minN; i < maxN; i++) {

            boolean isPrime = true;
            if(i%2==0 & i!=2){
                isPrime = false;
            }

            //check to see if the number is prime
            for (int j = 3; j <Math.sqrt(i); j=j+2) {

                if (i % j == 0 & j!=3) {
                    isPrime = false;
                    break;
                }
            }
            // print the number
            if (isPrime) {
                tabPrimesInRange[count] = Integer.toString(i);
                count++;
            }
        }
        return tabPrimesInRange;
    }
}
